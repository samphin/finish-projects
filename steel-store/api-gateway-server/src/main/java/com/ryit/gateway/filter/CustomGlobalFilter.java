package com.ryit.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.JwtConstant;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

/**
 * 全局过滤器
 *
 * @author samphin
 * @since 2019-9-28 14:35:58
 */
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 允许授权访问的前缀
     */
    @Value("#{'${allowed-prefix}'.split(',')}")
    public Set<String> allowedPrefix;

    /**
     * 白名单请求地址（不参与token校验的）
     */
    @Value("#{'${jwt.ignore-url}'.split(',')}")
    public Set<String> ignoreUrl;

    /**
     * 缓新token时间的过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    /**
     * 访问token的过期时间
     */
    @Value("${accessTokenExpireTime}")
    private String accessTokenExpireTime;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求地址，判断请求地址如果存在于白名单中，则不需要token认证
        ServerHttpRequest request = exchange.getRequest();
        //获取请求地址
        String requestUrl = request.getPath().toString();
        //创建响应体
        ServerHttpResponse response = exchange.getResponse();
        //判断请求的url中是否包含api或者admin，如果没包含，则非法请求
        boolean isExistPrefix = allowedPrefix.stream().anyMatch(prefix -> prefix.equals(subPrefix(requestUrl)));
        //创建返回数据对象
        ResponseData responseData = new ResponseData();
        //如果不包含，则定为非法请求
        if (!isExistPrefix) {
            responseData.setCode(SystemErrorEnum.ILLEGAL_REQUEST_ERROR.getErrorCode());
            responseData.setMsg(SystemErrorEnum.ILLEGAL_REQUEST_ERROR.getErrorMsg());
            return getResponseInfo(responseData, response);
        }
        //获取当前请求的url，判断是否在白名单中
        Optional<String> urlOptional = ignoreUrl.stream().filter(url -> requestUrl.contains(url)).findFirst();
        if (!urlOptional.isPresent()) {
            //获取jwtToken
            String jwtToken = request.getHeaders().getFirst(JwtConstant.AUTHORIZATION);

            //token非空，拦截非法请求
            if (StringUtils.isEmpty(jwtToken)) {
                responseData.setCode(SystemErrorEnum.TOKEN_IS_EMPTY.getErrorCode());
                responseData.setMsg(SystemErrorEnum.TOKEN_IS_EMPTY.getErrorMsg());
                return getResponseInfo(responseData, response);
            } else {
                try {
                    // 解密token获得account
                    String account = JwtUtil.getClaim(jwtToken, JwtConstant.ACCOUNT);
                    // 开始认证，要Authorization认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
                    if (JwtUtil.verify(jwtToken) && redisUtil.hasKey(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account))) {
                        // 获取RefreshToken的时间戳
                        String currentTimeMillisRedis = redisUtil.get(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account));
                        // 获取Authorization时间戳，与RefreshToken的时间戳对比
                        if (JwtUtil.getClaim(jwtToken, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                            //延长用户缓存时间
                            redisUtil.expire(String.format(RedisConstants.PREFIX_USER_INFO, account), Integer.parseInt(accessTokenExpireTime));
                            //延长用户token刷新的过期时间
                            redisUtil.expire(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account), Integer.parseInt(refreshTokenExpireTime));
                            //将现在的request，添加当前身份
                            ServerHttpRequest mutableReq = request.mutate().header(JwtConstant.AUTHORIZATION, jwtToken).build();
                            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                            return multipleOrigin(mutableExchange, chain);
                        } else {
                            responseData.setCode(SystemErrorEnum.PLEASE_LOGIN.getErrorCode());
                            responseData.setMsg(SystemErrorEnum.PLEASE_LOGIN.getErrorMsg());
                            return getResponseInfo(responseData, response);
                        }
                    } else {
                        //如果redis不存在用户信息，则说明失效了，重新登录
                        responseData.setCode(SystemErrorEnum.USER_NO_EXIST.getErrorCode());
                        responseData.setMsg(SystemErrorEnum.USER_NO_EXIST.getErrorMsg());
                        return getResponseInfo(responseData, response);
                    }
                } catch (Exception e) {
                    responseData.setCode(SystemErrorEnum.TOKEN_VERIFICATION.getErrorCode());
                    responseData.setMsg(SystemErrorEnum.TOKEN_VERIFICATION.getErrorMsg());
                    return getResponseInfo(responseData, response);
                }
            }
        }
        return multipleOrigin(exchange, chain);
    }

    /**
     * 解决SpringCloudGateway2.0中响应头中出现两个origin的Bug
     */
    private Mono<Void> multipleOrigin(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.defer(() -> {
            exchange.getResponse().getHeaders().entrySet().stream()
                    .filter(kv -> (kv.getValue() != null && kv.getValue().size() > 1))
                    .filter(kv -> (kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                            || kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)))
                    .forEach(kv -> {
                        kv.setValue(new ArrayList<String>() {{
                            add(kv.getValue().get(0));
                        }});
                    });

            return chain.filter(exchange);
        }));
    }

    /**
     * 截取请求url前缀
     *
     * @param requestUrl 请求地址
     * @return
     */
    private static String subPrefix(String requestUrl) {
        String newUrl = requestUrl.substring(requestUrl.indexOf("//") + 2);
        String[] split = newUrl.split("/");
        return split[1];
    }

    /**
     * 获取响应信息
     *
     * @param responseData 错误信息对象
     * @param response
     * @return
     */
    private Mono<Void> getResponseInfo(ResponseData responseData, ServerHttpResponse response) {
        byte[] bits = JSONObject.toJSONString(responseData).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
        Mono<Void> voidMono = response.writeWith(Mono.just(buffer));
        return voidMono;
    }

    @Override
    public int getOrder() {
        //return Ordered.HIGHEST_PRECEDENCE;
        // 指定此过滤器位于NettyWriteResponseFilter之后
        // 即待处理完响应体后接着处理响应头
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }
}

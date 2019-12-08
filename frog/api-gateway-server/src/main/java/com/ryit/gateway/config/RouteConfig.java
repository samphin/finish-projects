package com.ryit.gateway.config;

import com.ryit.gateway.filter.CustomGlobalFilter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * @author samphin
 * @since 2019-9-28 14:32:06
 */
@Configuration
public class RouteConfig {
    /**
     * 自定义的请求头
     */
    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, content-type, Authorization, credential, x-xsrf-token, account";
    /**
     * 允许请求方式
     */
    private static final String ALLOWED_METHODS = "POST,GET,PUT,PATCH,OPTIONS,DELETE";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_EXPOSE = "*";
    private static final String MAX_AGE = "3600";
    private static final String ALLOW_CREDENTIALS = "true";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
                //响应头必须与请求头保持一致（也可以前后端约定规范）
                /*
                HttpHeaders requestHeaders = request.getHeaders();
                List<String> accessControlRequestHeaders = requestHeaders.getAccessControlRequestHeaders();
                headers.addAll(ACCESS_CONTROL_ALLOW_HEADERS, accessControlRequestHeaders);*/
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOW_CREDENTIALS);
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALLOWED_EXPOSE);
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

    /**
     * 如果使用了注册中心（如：Eureka），进行控制则需要增加如下配置
     */
    @Bean
    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
        DiscoveryLocatorProperties properties = new DiscoveryLocatorProperties();
        //属性配置暂定
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
    }

    /**
     * 系统全局过滤器
     *
     * @return
     */
    @Bean
    public CustomGlobalFilter authSignatureFilter() {
        return new CustomGlobalFilter();
    }

    /**
     * 转发请求
     *
     * @return
     * @author samphin
     * @since 2019-10-17 16:00:48
     */
    /*@Bean
    public ForwardGatewayFilterFactory forwardGatewayFilterFactory() {
        return new ForwardGatewayFilterFactory();
    }*/

    /**
     * springboot2.0.5以上，gateway出现方法过滤bug
     *
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }
}

package com.ryit.security.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.JwtConstant;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import com.ryit.security.feign.ISysUserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : samphin
 * @since : 2019/8/19 10:32
 */

@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

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

    @Resource
    private RedisUtil redisUtil;


    @Autowired
    private ISysUserFeignClient sysUserFeignClient;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        log.info("shiro开始执行权限校验");

        // 查询用户是否存在
        ObjectMapper objectMapper = new ObjectMapper();

        //获取缓存的用户信息
        Object primaryPrincipal = principals.getPrimaryPrincipal();

        SysUser user = objectMapper.convertValue(primaryPrincipal, SysUser.class);

        //权限认证对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //查询用户角色权限信息
        /*ResponseData<List<SysSimpleRoleVo>> responseData = sysUserFeignClient.queryUserHaveRoles(user.getId());
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        List<SysSimpleRoleVo> roleVoList = responseData.getData();
        List<String> roles = roleVoList.stream().map(SysSimpleRoleVo::getCode).collect(Collectors.toList());*/
        //设置角色信息（查询角色信息）
        /*authorizationInfo.addRoles(roles);*/

        //设置权限信息（查询角色信息）
        //authorizationInfo.addStringPermissions(rolesPermissionsVo.getPermissions());

        return authorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, JwtConstant.ACCOUNT);
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        ObjectMapper objectMapper = new ObjectMapper();

        SysUser user = objectMapper.convertValue(redisUtil.getObject(String.format(RedisConstants.PREFIX_USER_INFO, account)), SysUser.class);

        if (user == null) {
            throw new AuthenticationException("该帐号不存在");
        }
        // 开始认证，要Authorization认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && redisUtil.hasKey(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account))) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = redisUtil.get(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account));
            // 获取Authorization时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                //创建用户缓存
                redisUtil.set(String.format(RedisConstants.PREFIX_USER_INFO, account), user,
                        Integer.parseInt(accessTokenExpireTime));
                //将user对象作为主体内容，shiro-redis插件需要用对象中id属性作为缓存key
                return new SimpleAuthenticationInfo(user, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}

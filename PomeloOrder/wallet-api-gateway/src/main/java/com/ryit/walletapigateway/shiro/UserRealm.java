package com.ryit.walletapigateway.shiro;

import com.netflix.zuul.context.RequestContext;
import com.ryit.commons.constant.JwtConstant;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.shiro.JwtToken;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import com.ryit.walletapigateway.feign.WalletUserFeignClient;
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

/**
 * @author : 刘修
 * @Date : 2019/8/19 10:32
 */

@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private WalletUserFeignClient walletUserFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;


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
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = JwtUtil.getClaim(principals.toString(), JwtConstant.ACCOUNT);
        // 查询用户角色
//        List<Role> roles = roleMapper.getRoleByMobile(account);
//        for (int i = 0, roleLen = roles.size(); i < roleLen; i++) {
//            Role role = roles.get(i);
        // 添加角色
//            simpleAuthorizationInfo.addRole(role.getName());
        // 根据用户角色查询权限
//            List<Permission> permissions = permissionMapper.getPermissionByRoleId(role.getId());
//            for (int j = 0, perLen = permissions.size(); j < perLen; j++) {
//                Permission permission = permissions.get(j);
        // 添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getSn());
//            }
//        }
        return simpleAuthorizationInfo;
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
        WalletUser user = (WalletUser) redisUtil.getObject(String.format(RedisConstants.PREFIX_WALLET_USER, account));
        if (null == user) {
            user = walletUserFeignClient.getWalletUserByPhone(account);
        }
        if (user == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && redisUtil.hasKey(String.format(RedisConstants.PREFIX_WALLET_API_REFRESH_TOKEN, account))) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = redisUtil.get(String.format(RedisConstants.PREFIX_WALLET_API_REFRESH_TOKEN, account));
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                //将用户传到其他服务
                //或者在配置文件使用sensitive-headers: Cookie,Set-Cookie 过滤掉Cookie,Set-Cookie使Authorization通行
                RequestContext.getCurrentContext().addZuulRequestHeader(JwtConstant.ACCOUNT, user.getId().toString());
                //创建用户缓存
                redisUtil.set(String.format(RedisConstants.PREFIX_WALLET_USER, account), user,
                        Integer.parseInt(refreshTokenExpireTime));
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }

}

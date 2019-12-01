package com.ryit.commons.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author : 刘修
 * @Date : 2019/8/19 10:29
 */
public class JwtToken implements AuthenticationToken {

    private String token;


    public JwtToken (String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}

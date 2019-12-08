package com.ryit.commons.constants;

/**
 * @author : samphin
 * @since : 2019-10-16 15:34:56
 */
public class JwtConstant {

    /**
     * 授权token
     */
    public static final String OAUTH_TOKEN_URL = "oauth/token";

    /**
     * 访问token
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 活跃状态
     */
    public static final String ACTIVE = "active";

    /**
     * jwt 账号redis key
     */
    public static final String ACCOUNT = "account";

    /**
     * jwt 时间戳redis key
     */
    public static final String CURRENT_TIME_MILLIS = "current_time_millis";
}

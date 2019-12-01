package com.ryit.commons.constants;

/**
 * Redis key常量
 *
 * @author samphin
 * @since 2019-10-16 16:11:34
 */
public class RedisConstants {

    /**
     * 手机验证码 redis key
     */
    public static final String PHONE_CODE_KEY = "phone_code_key_%s";

    /**
     * shiro token redis key store api
     */
    public static final String PREFIX_API_REFRESH_TOKEN = "store_api_refresh_token_%s";

    /**
     * shiro token redis key store monitor
     */
    public static final String PREFIX_ADMIN_REFRESH_TOKEN = "store_admin_refresh_token_%s";

    /**
     * shiro缓存Key
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro_cache_%s";

    /**
     * 用户信息Key
     */
    public static final String PREFIX_USER_INFO = "user_info_%s";
}

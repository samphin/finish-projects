package com.ryit.commons.constant;

/**
 * Redis key常量
 *
 * @author 武汉软艺
 * @Date 2018/12/27
 */
public class RedisConstants {

    /**
     * 手机验证码 redis key
     */
    public static final String PHONE_CODE_KEY = "phone_code_key_%s";

    /**
     * shirotoken redis key credit api
     */
    public static final String PREFIX_CREDIT_API_REFRESH_TOKEN = "credit_api_refresh_token_%s";

    /**
     * shirotoken redis key credit web
     */
    public static final String PREFIX_CREDIT_WEB_REFRESH_TOKEN = "credit_web_refresh_token_%s";

    /**
     * shirotoken redis key credit api
     */
    public static final String PREFIX_WALLET_API_REFRESH_TOKEN = "wallet_api_refresh_token_%s";

    /**
     * shirotoken redis key credit web
     */
    public static final String PREFIX_WALLET_WEB_REFRESH_TOKEN = "wallet_web_refresh_token_%s";

    /**
     * SHIRO_CACHE redis key
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro_cache_%s";

    /**
     * SHIRO_CACHE redis key
     */
    public static final String PREFIX_CREDIT_USER = "credit_user_%s";

    /**
     * SHIRO_CACHE redis key
     */
    public static final String PREFIX_WALLET_USER = "wallet_user_%s";

    /**
     * 钱包资讯 redis key
     */
    public static final String PREFIX_WALLET_NEWS = "wallet_news_%s";

    /**
     * 微信商户订单号key
     */
    public static final String WECHAT_OUT_TRADE_NO = "wechat_out_trade_no_%s";
}


package com.ryit.credituserserver.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 自定义微信配置类
 * @author samphin
 * @date 2019-9-21 17:44:32
 */
@Configuration
public class MyWeChatPayConfig implements WXPayConfig {

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.mch_id}")
    private String mch_id;
    @Value("${wechat.app_secret}")
    private String app_secret;
    @Value("${wechat.cert_path}")
    private String cert_path;
    @Value("${wechat.notify_url}")
    private String notify_url;
    @Value("${wechat.spbill_create_ip}")
    private String spbill_create_ip;

    /** 加载证书  这里证书需要到微信商户平台进行下载*/
    /*private byte [] certData;

    public MyWeChatPayConfig() throws  Exception{
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/wxpay/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }*/

    @Override
    public String getAppID() {
        return appid;
    }

    @Override
    public String getMchID() {
        return mch_id;
    }

    @Override
    public String getKey() {
        return app_secret;
    }

    @Override
    public InputStream getCertStream() {
        //return new ByteArrayInputStream(this.certData);
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }

    public String getCert_path() {
        return cert_path;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }
}

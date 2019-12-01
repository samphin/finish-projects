package com.ryit.talk;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 客服服务
 *
 * @author samphin
 * @since 2019-10-17 09:29:35
 */
@SpringCloudApplication
@ComponentScan({"com.ryit.talk.*"})
public class TalkServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalkServerApplication.class, args);
    }
}

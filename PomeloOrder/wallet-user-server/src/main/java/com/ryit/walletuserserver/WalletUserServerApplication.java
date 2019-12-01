package com.ryit.walletuserserver;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDistributedTransaction
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication(scanBasePackages = "com.ryit", exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ryit.walletuserserver.dao.**")
public class WalletUserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletUserServerApplication.class, args);
    }

}

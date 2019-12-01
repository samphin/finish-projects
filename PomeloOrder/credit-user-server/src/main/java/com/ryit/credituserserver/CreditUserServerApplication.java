package com.ryit.credituserserver;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDistributedTransaction
@EnableFeignClients
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.ryit", exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ryit.credituserserver.dao.**")
public class CreditUserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditUserServerApplication.class, args);
    }

}

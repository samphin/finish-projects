package com.ryit.credithelpserver;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDistributedTransaction
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.ryit", exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ryit.credithelpserver.dao.**")
public class CreditHelpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditHelpServerApplication.class, args);
    }

}

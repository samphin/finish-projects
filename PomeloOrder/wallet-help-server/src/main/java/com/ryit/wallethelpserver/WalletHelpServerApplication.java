package com.ryit.wallethelpserver;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDistributedTransaction
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.ryit", exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ryit.wallethelpserver.dao.**")
public class WalletHelpServerApplication {

    public static void main (String[] args) {
        SpringApplication.run(WalletHelpServerApplication.class, args);
    }

}

package com.ryit.walletmarketserver;

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
@MapperScan("com.ryit.walletmarketserver.dao.**")
public class WalletMarketServerApplication {

    public static void main (String[] args) {
        SpringApplication.run(WalletMarketServerApplication.class, args);
    }

}

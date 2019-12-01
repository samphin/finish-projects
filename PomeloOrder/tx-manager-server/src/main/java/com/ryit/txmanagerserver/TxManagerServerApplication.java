package com.ryit.txmanagerserver;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagerServer
public class TxManagerServerApplication {

    public static void main (String[] args) {
        SpringApplication.run(TxManagerServerApplication.class, args);
    }

}

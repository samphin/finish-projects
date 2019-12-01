package com.ryit.tm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 分布式事务管理中心启动类
 *
 * @author samphin
 * @since 2019-9-27 17:33:33
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagerServer
public class TxmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxmServerApplication.class, args);
    }

}

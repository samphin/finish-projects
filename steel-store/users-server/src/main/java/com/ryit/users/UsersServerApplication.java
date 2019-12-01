package com.ryit.users;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


@EnableDistributedTransaction
@MapperScan({"com.ryit.users.mapper"})
@SpringCloudApplication
@ComponentScan({"com.ryit"})
@EnableFeignClients
/**
 * 排除mongodb动态加载
 */
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class UsersServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersServerApplication.class, args);
    }
}

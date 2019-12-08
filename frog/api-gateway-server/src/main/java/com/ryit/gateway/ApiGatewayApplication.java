package com.ryit.gateway;

import com.ryit.commons.config.MongoDBConfig;
import com.ryit.commons.config.Swagger2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringCloudApplication
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class
})
@ComponentScan(value = "com.ryit", excludeFilters = {
        //排除加载Swagger2、Mongodb配置
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {Swagger2Config.class, MongoDBConfig.class})
})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

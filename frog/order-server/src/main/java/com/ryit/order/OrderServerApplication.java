package com.ryit.order;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 订单服务
 *
 * @author samphin
 * @since 2019-10-17 09:10:09
 */
@EnableDistributedTransaction
@MapperScan({"com.ryit.order.mapper"})
@SpringCloudApplication
@ComponentScan({"com.ryit"})
@EnableFeignClients
/**
 * 排除Rabbit动态加载
 */
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class})
public class OrderServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }
}

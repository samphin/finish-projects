package com.ryit.goods;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 商品服务
 *
 * @author samphin
 * @since 2019-10-17 09:27:50
 */
@EnableDistributedTransaction
@MapperScan({"com.ryit.goods.mapper"})
@SpringCloudApplication
@ComponentScan({"com.ryit"})
@EnableFeignClients
public class GoodsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsServerApplication.class, args);
    }
}

package com.ryit.creditwebgateway;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.ryit.creditwebgateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDistributedTransaction
@EnableZuulProxy
@EnableFeignClients
@EnableHystrix
@SpringBootApplication(scanBasePackages = "com.ryit")
public class CreditWebGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditWebGatewayApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }
}

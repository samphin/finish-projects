package com.ryit.creditapigateway;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.ryit.creditapigateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDistributedTransaction
@EnableHystrix
@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.ryit")
public class CreditApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditApiGatewayApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

}

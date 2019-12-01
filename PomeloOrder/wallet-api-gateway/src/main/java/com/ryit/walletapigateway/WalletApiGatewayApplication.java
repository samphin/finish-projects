package com.ryit.walletapigateway;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.ryit.walletapigateway.filter.AccessFilter;
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
public class WalletApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApiGatewayApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

}

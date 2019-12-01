package com.ryit.gateway.config;

import com.ryit.commons.constants.JwtConstant;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 限流配置
 */
@Configuration
public class RateLimiterConfig {

    /**
     * 根据远程请求地址
     *
     * @return
     */
    @Bean
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    /**
     * 按URL限流,即以每秒内请求数按URL分组统计，超出限流的url请求都将返回429状态
     *
     * @return
     */
    @Bean
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    /**
     * 按用户限流（现阶段以用户来限流）
     *
     * @return
     */
    @Bean
    @Primary
    KeyResolver tokenResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst(JwtConstant.AUTHORIZATION));
    }

    /**
     * 按IP来限流
     *
     * @return
     */
    @Bean
    KeyResolver ipResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}

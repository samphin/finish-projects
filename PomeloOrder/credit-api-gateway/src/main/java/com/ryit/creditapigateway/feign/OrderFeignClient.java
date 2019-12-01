package com.ryit.creditapigateway.feign;

import com.ryit.creditapigateway.feign.hystrix.OrderFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order-server",fallback = OrderFeignHystrix.class)
public interface OrderFeignClient {

    @GetMapping("/dict/pomeloFlag")
    String getPomeloFlag();

}

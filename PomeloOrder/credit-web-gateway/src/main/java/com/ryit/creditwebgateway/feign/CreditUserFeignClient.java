package com.ryit.creditwebgateway.feign;

import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.creditwebgateway.feign.hystrix.CreditUserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "credit-user-server",fallback = CreditUserFeignHystrix.class)
public interface CreditUserFeignClient {

    @GetMapping("/creditUser/getCreditUserByPhone")
    CreditUser getCreditUserByPhone(@RequestParam("phone") String phone);

}

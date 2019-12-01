package com.ryit.credituserserver.feign;

import com.alibaba.fastjson.JSONObject;
import com.ryit.credituserserver.feign.hystrix.CreditCouponFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "credit-coupon-server", fallback = CreditCouponFeignHystrix.class)
public interface CreditCouponFeignClient {

    @PostMapping("/creditCoupon/drawFreeOfChargeCoupon")
    void drawFreeOfChargeCoupon(@RequestBody JSONObject requestData);
}

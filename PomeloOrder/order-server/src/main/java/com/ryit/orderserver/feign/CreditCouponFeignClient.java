package com.ryit.orderserver.feign;

import com.ryit.orderserver.feign.hystrix.CreditCouponFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "credit-coupon-server",fallback = CreditCouponFeignHystrix.class)
public interface CreditCouponFeignClient {

    @PostMapping("/creditCoupon/updateCouponUseStatus")
    Boolean updateCouponUseStatus(@RequestParam("relationId") Long relationId);

    @GetMapping("/creditCoupon/queryDiscountCoin/{relationId}")
    Double queryDiscountCoin(@PathVariable("relationId") Long relationId);

}

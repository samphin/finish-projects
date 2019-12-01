package com.ryit.walletapigateway.feign;

import com.ryit.walletapigateway.feign.hystrix.SysOrderFeignClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: zhangweixun
 * @Date: 2019/9/25 0025下午 6:56
 */
@FeignClient(value = "order-server", fallback = SysOrderFeignClientHystrix.class)
public interface SysOrderFeignClient {

    /**
     * 用于提供查询用户24小时内提交的订单订单数量
     *
     * @param phone
     * @return
     */
    @GetMapping("/order/queryUserOrderCount")
    Integer queryOrderWithinDay(@RequestParam("phone") String phone);
}

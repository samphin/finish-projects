package com.ryit.walletuserserver.feign;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.pojo.SysOrderAptitude;
import com.ryit.walletuserserver.feign.hystrix.SysOrderFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/18 0018上午 10:11
 */
@FeignClient(value = "order-server", fallback = SysOrderFeignHystrix.class)
public interface SysOrderFeignClient {

    /**
     * 提交订单
     *
     * @param sysOrder
     * @return
     */
    @PostMapping("/order/anon/insertOrder")
    AjaxResult insertSysOrder(@RequestBody SysOrderDto sysOrder);

}

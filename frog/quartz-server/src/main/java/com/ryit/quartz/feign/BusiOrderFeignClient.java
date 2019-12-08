package com.ryit.quartz.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-server")
public interface BusiOrderFeignClient {

    /**
     * 查询所有未支付订单
     */
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/order/nopay")
    ResponseData<String> queryAllNoPayOrder(@RequestBody BusiOrderDto busiOrderDto);

    /**
     * 自动取消订单
     */
    @PutMapping(BaseUrlConstants.BASE_API_PREFIX + "/order/{id}/status")
    ResponseData autoCancel(@PathVariable("id") Long orderId);
}

package com.ryit.security.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-server")
public interface IBusiOrderFeignClient {

    /**
     * 查询用户订单数量
     *
     * @return
     */
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/{userId}/order/nums")
    ResponseData<Integer> queryUserOrderNums(@PathVariable("userId") Integer userId);
}

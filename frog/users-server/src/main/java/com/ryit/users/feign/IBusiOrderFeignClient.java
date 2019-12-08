package com.ryit.users.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiOrderStatisticDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-server")
public interface IBusiOrderFeignClient {

    /**
     * 营业额概括（总营业额、月营业额、日营业额，根据时间筛选）
     *
     * @param dto
     * @return
     */
    @GetMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/order/turnover")
    ResponseData<Double> queryOrdersTurnover(@ModelAttribute BusiOrderStatisticDto dto);

    /**
     * 查询用户订单数量
     *
     * @return
     */
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/{userId}/order/nums")
    ResponseData<Integer> queryUserOrderNums(@PathVariable("userId") Integer userId);
}

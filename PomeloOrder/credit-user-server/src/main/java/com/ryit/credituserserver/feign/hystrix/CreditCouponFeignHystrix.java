package com.ryit.credituserserver.feign.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credituserserver.feign.CreditCouponFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author : samphin
 * @Date : 2019-9-20 14:22:16
 */
@Component
@Slf4j
public class CreditCouponFeignHystrix implements CreditCouponFeignClient {

    @Override
    public void drawFreeOfChargeCoupon(JSONObject requestData) {
        log.error("用户" + requestData.getString("userId") + "获取免单券服务已中断");
        throw new CustomException("获取免单券服务已中断");
    }

}

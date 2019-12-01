package com.ryit.orderserver.feign.hystrix;

import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.feign.CreditCouponFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : 刘修
 * @Date : 2019/9/11 15:06
 */
@Component
public class CreditCouponFeignHystrix implements CreditCouponFeignClient {

    private Logger log = LoggerFactory.getLogger(CreditCouponFeignHystrix.class);

    @Override
    public Boolean updateCouponUseStatus (Long relationId) {
        log.error("更新优惠券使用状态时服务中断");
        throw new CustomException("更新优惠券使用状态时服务中断");
    }

    @Override
    public Double queryDiscountCoin (Long relationId) {
        log.error("查询优惠券优惠额度时服务中断");
        throw new CustomException("查询优惠券优惠额度时服务中断");
    }
}

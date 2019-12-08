package com.ryit.security.feign.impl;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.security.feign.IBusiOrderFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单服务熔断类
 *
 * @author samphin
 * @since 2019-11-25 10:09:47
 */
@Slf4j
public class BusiOrderFeignClientImpl implements FallbackFactory<IBusiOrderFeignClient> {

    @Override
    public IBusiOrderFeignClient create(Throwable cause) {
        log.error("订单服务熔断...", cause);
        return new IBusiOrderFeignClient() {

            @Override
            public ResponseData<Integer> queryUserOrderNums(Integer userId) {
                return ResponseData.failure(SystemErrorEnum.ORDER_NUM_ERROR).setData(0);
            }
        };
    }
}

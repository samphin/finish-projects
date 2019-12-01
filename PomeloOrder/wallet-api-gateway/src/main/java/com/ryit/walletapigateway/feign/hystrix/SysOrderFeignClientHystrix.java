package com.ryit.walletapigateway.feign.hystrix;

import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletapigateway.feign.SysOrderFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: zhangweixun
 * @Date: 2019/9/25 0025下午 6:57
 */
@Component
public class SysOrderFeignClientHystrix implements SysOrderFeignClient {

    private Logger log = LoggerFactory.getLogger(SysOrderFeignClientHystrix.class);

    @Override
    public Integer queryOrderWithinDay(String phone) {
        log.error("获取用户当日提交订单数量时服务中断");
        throw new CustomException("获取用户当日提交订单数量时服务中断");
    }
}

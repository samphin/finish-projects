package com.ryit.creditapigateway.feign.hystrix;

import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditapigateway.feign.OrderFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : 刘修
 * @Date : 2019/9/11 9:23
 */
@Component
public class OrderFeignHystrix implements OrderFeignClient {

    private Logger log = LoggerFactory.getLogger(OrderFeignHystrix.class);

    @Override
    public String getPomeloFlag () {
        log.error("获取APP审核状态服务中断");
        throw new CustomException("获取APP审核状态服务中断");
    }
}

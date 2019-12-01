package com.ryit.creditwebgateway.feign.hystrix;

import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditwebgateway.feign.CreditUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : 刘修
 * @Date : 2019/9/11 9:22
 */
@Component
public class CreditUserFeignHystrix implements CreditUserFeignClient {

    private Logger log = LoggerFactory.getLogger(CreditUserFeignHystrix.class);

    @Override
    public CreditUser getCreditUserByPhone (String phone) {
        log.error("请求用户信息服务中断");
        throw new CustomException("请求用户信息服务中断");
    }
}

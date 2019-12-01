package com.ryit.credithelpserver.feign.hystrix;

import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credithelpserver.feign.CreditUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: zhangweixun
 * @Date: 2019/10/18 0018下午 4:38
 */
@Component
public class CreditUserFeignHystrix implements CreditUserFeignClient {

    private Logger log = LoggerFactory.getLogger(CreditUserFeignHystrix.class);

    @Override
    public CreditUser getCreditUserByPhone(String phone) {
        log.error("电话查询用户信息服务中断");
        throw new CustomException("电话查询用户信息服务中断");
    }
}

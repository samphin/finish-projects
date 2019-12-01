package com.ryit.creditcouponserver.feign.hystrix;

import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.feign.CreditUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/18 0018下午 4:28
 */
@Component
public class CreditUserFeignHystrix implements CreditUserFeignClient {

    private Logger log = LoggerFactory.getLogger(CreditUserFeignHystrix.class);

    @Override
    public List<CreditUser> queryAllNotAdminCreditUsers() {
        log.error("获取所有非管理员用户服务中断");
        throw new CustomException("获取所有非管理员用户服务中断");
    }

    @Override
    public List<CreditUser> queryCreditUserListById(Long id) {
        log.error("查询用户信息服务中断");
        throw new CustomException("查询用户信息服务中断");
    }
}

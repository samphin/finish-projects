package com.ryit.creditapigateway.feign.hystrix;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditapigateway.feign.CreditUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : 刘修
 * @Date : 2019/9/11 9:22
 */
@Component
public class CreditUserFeignHystrix implements CreditUserFeignClient {

    private Logger log = LoggerFactory.getLogger(CreditUserFeignHystrix.class);

    @Override
    public CreditUser getCreditUserByPhone(String phone) {
        log.error("电话查询抢单用户服务中断");
        throw new CustomException("电话查询抢单用户服务中断");
    }

    @Override
    public Integer registerUser(CreditUser user) {
        log.error("新用户注册服务中断");
        throw new CustomException("新用户注册服务中断");
    }

    @Override
    public Boolean checkPhone(String phone) {
        log.error("校验手机号码服务中断");
        throw new CustomException("校验手机号码服务中断");
    }


    /**
     * 通过微信openid获取用户信息
     *
     * @author chenyongfeng
     * @author samphin
     * @date 2019-9-16 13:38:15
     */
    @Override
    public CreditUser queryCreditUserByOpenId(@RequestParam("openId") String openId) {
        log.error("微信OPENID获取用户信息服务中断");
        throw new CustomException("微信OPENID获取用户信息服务中断");
    }

    @Override
    public AjaxResult updateUser(CreditUser user) {
        log.error("微信注册用户信息更新服务中断");
        throw new CustomException("微信注册用户信息更新服务中断");
    }

    @Override
    public AjaxResult queryUserByPhone(String phone) {
        log.error("微信注册手机查询用户服务中断");
        throw new CustomException("微信注册手机查询用户服务中断");
    }
}

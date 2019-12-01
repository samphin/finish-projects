package com.ryit.walletapigateway.feign.hystrix;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletapigateway.feign.WalletUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : 刘修
 * @Date : 2019/9/11 9:22
 */
@Component
public class WalletUserFeignHystrix implements WalletUserFeignClient {

    private Logger log = LoggerFactory.getLogger(WalletUserFeignHystrix.class);


    @Override
    public WalletUser getWalletUserByPhone(String phone) {
        log.error("获取用户信息时服务中断");
        throw new CustomException("获取用户信息时服务中断");
    }

    @Override
    public Integer registerUser(WalletUser user) {
        log.error("注册时服务中断");
        throw new CustomException("注册时服务中断");
    }

    @Override
    public Boolean checkPhone(String phone) {
        log.error("校验手机号码时服务中断");
        throw new CustomException("校验手机号码时服务中断");
    }

    @Override
    public WalletUser queryUserByPhone(String phone) {
        log.error("获取用户信息时服务中断");
        throw new CustomException("获取用户信息时服务中断");
    }

    @Override
    public AjaxResult registerWalletUser(WalletUser user) {
        log.error("获取用户信息时服务中断");
        throw new CustomException("获取用户信息时服务中断");
    }

    @Override
    public AjaxResult queryByOpenId(String openId) {
        log.error("获取用户信息时服务中断");
        throw new CustomException("获取用户信息时服务中断");
    }

    @Override
    public AjaxResult updateUserOpenId(WalletUser walletUser) {
        log.error("保存用户openId信息时服务中断");
        throw new CustomException("保存用户openId信息时服务中断");
    }
}

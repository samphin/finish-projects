package com.ryit.orderserver.feign.hystrix;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.feign.WalletUserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author : samphin
 * @date : 2019-9-23 21:17:15
 */
@Component
@Slf4j
public class WalletUserFeignHystrix implements WalletUserFeignClient {

    @Override
    public WalletUser queryUserByPhone(String phone) {
        log.error("根据手机号查询钱包用户时服务中断");
        throw new CustomException("根据手机号查询钱包用户时服务中断");
    }

    @Override
    public WalletUser queryWalletUserById(Long id) {
        log.error("根据ID查询钱包用户时服务中断");
        throw new CustomException("根据ID查询钱包用户时服务中断");
    }

    @Override
    public AjaxResult insertWalletUser(WalletUser walletUser) {
        log.error("保存钱包用户时服务中断");
        throw new CustomException("保存钱包用户时服务中断");
    }

    @Override
    public AjaxResult updateOrderWalletor(WalletUser walletUser) {
        log.error("修改贷款人信息时服务中断");
        throw new CustomException("修改贷款人信息时服务中断");
    }

    @Override
    public AjaxResult queryOrderWalletor(Long orderId) {
        log.error("查询订单贷款人信息时服务中断");
        throw new CustomException("查询订单贷款人信息时服务中断");
    }
}

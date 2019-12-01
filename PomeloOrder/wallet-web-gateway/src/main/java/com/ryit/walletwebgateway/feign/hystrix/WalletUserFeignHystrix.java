package com.ryit.walletwebgateway.feign.hystrix;

import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletwebgateway.feign.WalletUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : 刘修
 * @Date : 2019/9/11 15:07
 */
@Component
public class WalletUserFeignHystrix implements WalletUserFeignClient {

    private Logger log = LoggerFactory.getLogger(WalletUserFeignHystrix.class);

    @Override
    public WalletUser getWalletUserByPhone (String phone) {
        log.error("获取用户信息时服务中断");
        throw new CustomException("获取用户信息时服务中断");
    }

}

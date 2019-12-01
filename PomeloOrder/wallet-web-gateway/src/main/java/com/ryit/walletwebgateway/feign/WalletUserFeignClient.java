package com.ryit.walletwebgateway.feign;

import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.walletwebgateway.feign.hystrix.WalletUserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "wallet-user-server",fallback = WalletUserFeignHystrix.class)
public interface WalletUserFeignClient {

    @GetMapping("/walletUser/getWalletUserByPhone")
    WalletUser getWalletUserByPhone(@RequestParam("phone") String phone);

}

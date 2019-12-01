package com.ryit.walletapigateway.feign;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.walletapigateway.feign.hystrix.WalletUserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "wallet-user-server", fallback = WalletUserFeignHystrix.class)
public interface WalletUserFeignClient {

    /**
     * 电话查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/walletUser/getWalletUserByPhone")
    WalletUser getWalletUserByPhone(@RequestParam("phone") String phone);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/walletUser/registerUser")
    Integer registerUser(@RequestBody WalletUser user);

    /**
     * 判断当前电话是否被注册
     *
     * @param phone
     * @return
     */
    @GetMapping("/walletUser/checkPhone")
    Boolean checkPhone(@RequestParam("phone") String phone);

    /**
     * 电话查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/walletUser/anon/getUser/{phone}")
    WalletUser queryUserByPhone(@PathVariable("phone") String phone);


    /**
     * h5注册新用户
     *
     * @param user
     * @return
     */
    @PostMapping("/walletUser/registerWalletUser")
    AjaxResult registerWalletUser(@RequestBody WalletUser user);

    /**
     * 微信用户openid查询用户信息
     *
     * @param openId
     * @return
     */
    @GetMapping("/walletUser/anon/getUserByOpenId/{openId}")
    AjaxResult queryByOpenId(@PathVariable("openId") String openId);

    /**
     * 用于已用手机号注册用户使用微信登录
     *
     * @param walletUser
     * @return
     */
    @PutMapping("/walletUser/updateUserOpenId")
    AjaxResult updateUserOpenId(@RequestBody WalletUser walletUser);
}

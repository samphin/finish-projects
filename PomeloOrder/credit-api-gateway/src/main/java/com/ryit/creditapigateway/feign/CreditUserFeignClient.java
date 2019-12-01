package com.ryit.creditapigateway.feign;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.creditapigateway.feign.hystrix.CreditUserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "credit-user-server", fallback = CreditUserFeignHystrix.class)
public interface CreditUserFeignClient {

    @GetMapping("/creditUser/getCreditUserByPhone")
    CreditUser getCreditUserByPhone(@RequestParam("phone") String phone);

    @PostMapping("/creditUser/registerUser")
    Integer registerUser(@RequestBody CreditUser user);

    @GetMapping("/creditUser/checkPhone")
    Boolean checkPhone(@RequestParam("phone") String phone);

    /**
     * 通过微信openid获取用户信息
     *
     * @author samphin
     * @date 2019-9-16 13:38:15
     */
    @GetMapping("/creditUser/queryCreditUserByOpenId")
    CreditUser queryCreditUserByOpenId(@RequestParam("openId") String openId);

    /**
     * 微信注册用户时，发现该手机号已存在，此时更新用户信息
     *
     * @author samphin
     * @date 2019-10-14 16:52:15
     */
    @PutMapping("/creditUser/updateUser")
    AjaxResult updateUser(@RequestBody CreditUser user);

    /**
     * 根据手机号查询抢单用户
     *
     * @author samphin
     * @date 2019-10-14 16:53:08
     */
    @GetMapping("/creditUser/queryUserByPhone/{phone}")
    AjaxResult queryUserByPhone(@PathVariable("phone") String phone);

}

package com.ryit.credithelpserver.feign;

import com.ryit.commons.entity.pojo.CreditUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "credit-user-server")
public interface CreditUserFeignClient {

    /**
     * 电话查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/creditUser/getCreditUserByPhone")
    CreditUser getCreditUserByPhone(@RequestParam("phone") String phone);
}

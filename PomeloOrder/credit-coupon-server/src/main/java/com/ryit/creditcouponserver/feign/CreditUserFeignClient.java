package com.ryit.creditcouponserver.feign;

import com.ryit.commons.entity.pojo.CreditUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "credit-user-server")
public interface CreditUserFeignClient {

    /**
     * 获取所有非管理员用户
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:49:07
     */
    @GetMapping("/creditUser/queryAllNotAdminCreditUsers")
    List<CreditUser> queryAllNotAdminCreditUsers();

    /**
     * 根据用户ID，查询用户信息
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:49:07
     */
    @GetMapping("/creditUser/queryCreditUserListById")
    List<CreditUser> queryCreditUserListById(@RequestParam("id") Long id);

}

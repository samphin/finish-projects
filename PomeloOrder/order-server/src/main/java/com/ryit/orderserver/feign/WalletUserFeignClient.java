package com.ryit.orderserver.feign;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.CreditBill;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.CreditBillVo;
import com.ryit.orderserver.feign.hystrix.CreditUserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "wallet-user-server",fallback = CreditUserFeignHystrix.class)
public interface WalletUserFeignClient {

    /**
     * 根据手机号查询钱包用户信息
     * @param phone
     * @return
     */
    @GetMapping("/walletUser/anon/getUser/{phone}")
    WalletUser queryUserByPhone(@PathVariable("phone") String phone);

    /**
     * 根据主键查询贷款人信息
     * @param id
     * @return
     */
    @GetMapping("/walletUser/queryWalletUser/{id}")
    WalletUser queryWalletUserById(@PathVariable("id") Long id);

    /**
     * 保存钱包用户基本信息
     * @param walletUser
     */
    @PostMapping("/walletUser/anon/submissionUser")
    AjaxResult insertWalletUser(@RequestBody WalletUser walletUser);

    /**
     * 修改贷款人身份证信息
     * @author samphin
     * @date 2019-10-14 14:13:38
     */
    @PutMapping("/walletUser/updateIdCard")
    AjaxResult updateOrderWalletor(@RequestBody WalletUser walletUser);

    /**
     * 查询订单贷款人信息
     * @param orderId
     * @return
     */
    @GetMapping("/walletUser/queryOrderWalletor/{orderId}")
    AjaxResult queryOrderWalletor(@PathVariable("orderId")Long orderId);
}
package com.ryit.orderserver.feign;

import com.ryit.commons.entity.pojo.CreditBill;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.vo.CreditBillVo;
import com.ryit.orderserver.feign.hystrix.CreditUserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "credit-user-server",fallback = CreditUserFeignHystrix.class)
public interface CreditUserFeignClient {

    @GetMapping("/creditUser/getCreditUserByPhone")
    CreditUser getCreditUserByPhone(@RequestParam("phone") String phone);

    @PostMapping("/creditBill/saveBill")
    Long saveBill(@RequestBody(required = false) CreditBill creditBill);

    @GetMapping("/creditBill/admin/getPayBill")
    CreditBillVo getPayBill(@RequestParam("payId")Long payId);

    @PostMapping("/creditUser/updateCreditUserCoin")
    Integer updateCreditUserCoin(@RequestParam("id") Long id, @RequestParam("coin") Double coin);

    @PostMapping("/creditUser/addBackOrderNum")
    void addBackOrderNum(@RequestParam("id") Long id);

    @PutMapping("/creditUser/subtractBackOrderNum")
    void subtractBackOrderNum(@RequestParam("id") Long id,@RequestParam("coin") Double coin);

    @PostMapping("/creditBill/updateBill")
    Integer updateBill(@RequestBody CreditBill creditBill);

    @GetMapping("/creditUser/getBackOrderNum")
    Integer getBackOrderNum(@RequestParam("id") Long id);

    @GetMapping("/creditUser/getCreditUserCoin")
    Double getCreditUserCoin(@RequestParam("id") Long id);

    /**
     * 根据用户ID，查询用户信息
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:49:07
     */
    @GetMapping("/creditUser/queryCreditUserListById")
    List<CreditUser> queryCreditUserListById(@RequestParam("id") Long id);

    /**
     * 注册用户
     * @param user
     * @author samphin
     * @date 2019-9-23 15:57:053
     */
    @PostMapping("/creditUser/registerUserCallBack")
    Long registerUserCallBack(@RequestBody CreditUser user);
}

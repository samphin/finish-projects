package com.ryit.orderserver.feign.hystrix;

import com.ryit.commons.entity.pojo.CreditBill;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.vo.CreditBillVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.feign.CreditUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/9/11 15:07
 */
@Component
public class CreditUserFeignHystrix implements CreditUserFeignClient {

    private Logger log = LoggerFactory.getLogger(CreditUserFeignHystrix.class);

    @Override
    public CreditUser getCreditUserByPhone (String phone) {
        log.error("请求用户信息时服务中断");
        throw new CustomException("请求用户信息时服务中断");
    }

    @Override
    public Long saveBill (CreditBill creditBill) {
        log.error("保存账单时服务中断");
        throw new CustomException("保存账单时服务中断");
    }

    @Override
    public CreditBillVo getPayBill (Long payId) {
        log.error("查询账单时服务中断");
        throw new CustomException("查询账单时服务中断");
    }

    @Override
    public Integer updateCreditUserCoin (Long id, Double coin) {
        log.error("更新用户余额时服务中断");
        throw new CustomException("更新用户余额时服务中断");
    }

    @Override
    public void addBackOrderNum (Long id) {
        log.error("添加用户退单次数时服务中断");
        throw new CustomException("添加用户退单次数时服务中断");
    }

    @Override
    public void subtractBackOrderNum (Long id, Double coin) {
        log.error("减少用户退单次数时服务中断");
        throw new CustomException("减少用户退单次数时服务中断");
    }

    @Override
    public Integer updateBill (CreditBill creditBill) {
        log.error("更新账单时服务中断");
        throw new CustomException("更新账单时服务中断");
    }

    @Override
    public Integer getBackOrderNum (Long id) {
        log.error("获取用户退单次数时服务中断");
        throw new CustomException("获取用户退单次数时服务中断");
    }

    @Override
    public Double getCreditUserCoin (Long id) {
        log.error("获取用户余额时服务中断");
        throw new CustomException("获取用户余额时服务中断");
    }

    @Override
    public List<CreditUser> queryCreditUserListById(Long id) {
        log.error("查询用户信息时服务中断");
        throw new CustomException("查询用户信息时服务中断");
    }

    @Override
    public Long registerUserCallBack(CreditUser user) {
        log.error("订单保存时贷款用户注册时服务中断");
        throw new CustomException("订单保存时贷款用户注册时服务中断");
    }
}

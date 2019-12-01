package com.ryit.creditcouponserver.dao;


import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.CreditRecharge;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRechargeMapper extends BaseMapper<Long, CreditRecharge> {

    /**
     * 根据金额查询充值方案记录条数
     * @param creditRecharge
     * @return
     */
    Integer selectRechargeByMoney(CreditRecharge creditRecharge);
}
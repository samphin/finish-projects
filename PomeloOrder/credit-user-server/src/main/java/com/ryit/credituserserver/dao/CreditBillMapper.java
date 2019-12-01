package com.ryit.credituserserver.dao;

import com.ryit.commons.entity.pojo.CreditBill;
import com.ryit.commons.entity.pojo.CreditBillStatistics;
import com.ryit.commons.entity.pojo.CreditInvite;
import com.ryit.commons.entity.pojo.CreditRecharge;
import com.ryit.commons.entity.vo.CreditBillVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CreditBillMapper {

    Long insertCreditBill(CreditBill record);

    List<CreditBillVo> getBillListByCreditUser(Long account);

    Map<String, Double> getBillCountByCreditUser(Long account);

    List<CreditRecharge> getRechargeType(Integer num);

    CreditRecharge getRecharge(Long id);

    List<CreditInvite> getCreditInvite();

    Integer countGrab(Long userId);

    CreditBill selectPayBill(@Param("payId")Long payId);

    /**
     * 查询流水列表
     * @param map
     * @author samphin
     * @date 2019-9-2 16:15:05
     * @return
     */
    List<CreditBill> selectAllByCondition(Map map);

    /**
     * 根据时间节点，统计账单流水信息
     * @author samphin
     * @date 2019-9-2 19:00:39
     */
    List<CreditBillStatistics> selectCreditBillStatistics(Map map);
}
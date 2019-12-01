package com.ryit.credituserserver.service;

import com.ryit.commons.entity.dto.CreditBillQueryDto;
import com.ryit.commons.entity.pojo.CreditBill;
import com.ryit.commons.entity.pojo.CreditRecharge;
import com.ryit.commons.entity.vo.CreditBillListVo;
import com.ryit.commons.entity.vo.CreditBillStatisticsVo;
import com.ryit.commons.entity.vo.CreditBillVo;

import java.util.List;
import java.util.Map;

public interface CreditBillService {

    /**
     * 保存账单信息
     * @param creditBill
     * @return
     */
    Long saveBill(CreditBill creditBill);

    /**
     * 根据用户ID，获取所有账单流水信息
     * @param account
     * @return
     */
    List<CreditBillVo> getBillListByCreditUser(Long account);

    Map<String, Double> getBillCountByCreditUser(Long account);

    /**
     * 根据充值类型查询充值方案
     * @param num
     * @return
     */
    List<CreditRecharge> getRechargeType(Integer num);

    Integer rechargeCoin(Long userId, Long rechargeId);

    /**
     * 通过支付用户ID查询流水详情
     *
     * @param payId
     * @return
     * @author samphin
     * @date 2019-9-2 17:15:23
     */
    CreditBillVo getPayBill(Long payId);

    /**
     * 查询PC端账单流水列表
     *
     * @param queryDto
     * @return
     * @author samphin
     * @date 2019-9-2 17:15:35
     */
    List<CreditBillListVo> queryAllByCondition(CreditBillQueryDto queryDto);

    /**
     * 统计今日、本月、本年账单流水
     *
     * @param year
     * @param month
     * @param day
     * @return
     * @author samphin
     * @date 2019-9-2 18:04:21
     */
    CreditBillStatisticsVo queryCreditBillStatistics(String year, String month, String day);
}

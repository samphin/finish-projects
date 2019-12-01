package com.ryit.credituserserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditBillQueryDto;
import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.entity.pojo.*;
import com.ryit.commons.entity.vo.CreditBillListVo;
import com.ryit.commons.entity.vo.CreditBillStatisticsVo;
import com.ryit.commons.entity.vo.CreditBillVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credituserserver.dao.CreditBillMapper;
import com.ryit.credituserserver.dao.CreditUserMapper;
import com.ryit.credituserserver.feign.MessageFeignClient;
import com.ryit.credituserserver.service.CreditBillService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : 刘修
 * @Date : 2019/8/22 17:36
 */
@Service
public class CreditBillServiceImpl implements CreditBillService {

    @Autowired
    private CreditBillMapper creditBillMapper;

    @Autowired
    private CreditUserMapper creditUserMapper;

    @Resource
    private MessageFeignClient messageFeignClient;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long saveBill(CreditBill creditBill) {
        try {
            creditBillMapper.insertCreditBill(creditBill);
            return creditBill.getId();
        } catch (Exception e) {
            throw new CustomException("保存单流水失败");
        }
    }

    @Override
    public List<CreditBillVo> getBillListByCreditUser(Long account) {
        try {
            return creditBillMapper.getBillListByCreditUser(account);
        } catch (Exception e) {
            throw new CustomException("查询用户账单流水列表失败");
        }
    }

    @Override
    public Map<String, Double> getBillCountByCreditUser(Long account) {
        try {
            return creditBillMapper.getBillCountByCreditUser(account);
        } catch (Exception e) {
            throw new CustomException("查询用户账单流水列表失败");
        }
    }

    @Override
    public List<CreditRecharge> getRechargeType(Integer num) {
        try {
            return creditBillMapper.getRechargeType(num);
        } catch (Exception e) {
            throw new CustomException("查询充值方案失败");
        }
    }

    /**
     * 更新用户余额
     *
     * @param userId
     * @param rechargeId
     * @author samphin
     * @date 2019-9-23 14:35:41
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer rechargeCoin(Long userId, Long rechargeId) {
        CreditRecharge recharge = creditBillMapper.getRecharge(rechargeId);
        Map<String, Object> map = new HashMap<>(6);
        //首充处理逻辑
        if (recharge.getRechargeType() == 0) {
            CreditUser user = creditUserMapper.getCreditUserById(userId);
            double day = (System.currentTimeMillis() - user.getRegisterTime().getTime()) / 1000.0 / 60.0 / 60.0 / 24.0;
            if (day <= 7) {
                //查询所有邀请方案信息
                List<CreditInvite> inviteList = creditBillMapper.getCreditInvite();
                //查询当前用户邀请人信息
                CreditUser creditUser = creditUserMapper.getCreditUserById(user.getUpUser());
                //邀请人信息不为空时，更新邀请人余额信息
                if (null != creditUser) {
                    Map<Integer, Double> inviteMap = new HashMap<>();
                    Double coinMax = 0d;
                    Integer numMax = 0;
                    for (CreditInvite invite : inviteList) {
                        inviteMap.put(invite.getNum(), invite.getCoin());
                        if (invite.getNum() > numMax) {
                            numMax = invite.getNum();
                            coinMax = invite.getCoin();
                        }
                    }
                    Double coin = inviteMap.get(creditUser.getInviteNum());
                    if (null == coin) {
                        coin = coinMax;
                    }
                    map.put("id", creditUser.getId());
                    map.put("inviteNum", 1);
                    map.put("coin", coin);
                    creditUserMapper.updateCreditUserCoin(map);
                }
            }
        }
        Double realCoin = recharge.getMoney() + recharge.getGift();
        CreditBill bill = new CreditBill();
        bill.setBillType(0);
        bill.setBillCoin(realCoin);
        bill.setUserId(userId);
        bill.setBillMoney(recharge.getMoney());
        creditBillMapper.insertCreditBill(bill);
        map.remove("inviteNum");
        map.put("id", userId);
        map.put("coin", realCoin);
        List<CreditUser> users = this.creditUserMapper.selectListByPrimaryKey(userId);
        PushMessageDto messageDto = new PushMessageDto();
        messageDto.setTitle("充值提醒");
        messageDto.setContent(DateFormatUtils.format(new Date(), "yyyy年MM月dd日") + "成功充值" + realCoin + "元");
        messageDto.setUsers(users);
        //柚子抢单消息
        messageDto.setAppType("credit");
        //极光推送消息给充值用户
        messageFeignClient.push(messageDto);
        return creditUserMapper.updateCreditUserCoin(map);
    }

    @Override
    public CreditBillVo getPayBill(Long payId) {

        CreditBill po = creditBillMapper.selectPayBill(payId);

        CreditBillVo vo = new CreditBillVo().buildVo(po);

        return vo;
    }

    @Override
    public List<CreditBillListVo> queryAllByCondition(CreditBillQueryDto queryDto) {
        Map<String, Object> queryParam = new HashMap(4);
        queryParam.put("userName", queryDto.getUserName());
        queryParam.put("billType", queryDto.getBillType());
        queryParam.put("startTime", queryDto.getStartTime());
        queryParam.put("endTime", queryDto.getEndTime());
        List<CreditBill> poList = creditBillMapper.selectAllByCondition(queryParam);
        return CreditBillListVo.buildVoList(poList);
    }

    @Override
    public CreditBillStatisticsVo queryCreditBillStatistics(String year, String month, String day) {
        Map<String, String> dateMap = calculateBillTime(year, month, day);
        CreditBillStatisticsVo vo = new CreditBillStatisticsVo();
        List<CreditBillStatistics> creditBillStatisticsList = creditBillMapper.selectCreditBillStatistics(dateMap);
        if (CollectionUtils.isEmpty(creditBillStatisticsList)) {
            return vo;
        }

        //抢单信息
        JSONObject consumeInfo = new JSONObject();

        //退单信息
        JSONObject backOrderInfo = new JSONObject();

        //充值信息
        JSONObject rechargeInfo = new JSONObject();

        //充值
        Optional<CreditBillStatistics> rechargeOptional = creditBillStatisticsList.stream().filter(bill -> 0 == bill.getBillType()).findFirst();
        rechargeOptional.ifPresent(billStatistics -> {
            rechargeInfo.put("count", billStatistics.getTotalCount());
            rechargeInfo.put("coin", billStatistics.getTotalCoin());
            rechargeInfo.put("rechargeMoney", billStatistics.getTotalRechargeMoney());
            vo.setRechargeInfo(rechargeInfo);
        });

        //抢单
        Optional<CreditBillStatistics> consumeOptional = creditBillStatisticsList.stream().filter(bill -> 1 == bill.getBillType()).findFirst();
        consumeOptional.ifPresent(billStatistics -> {
            consumeInfo.put("count", billStatistics.getTotalCount());
            consumeInfo.put("coin", billStatistics.getTotalCoin());
            vo.setConsumeInfo(consumeInfo);
        });
        //退单
        Optional<CreditBillStatistics> backOrderOptional = creditBillStatisticsList.stream().filter(bill -> 2 == bill.getBillType()).findFirst();
        backOrderOptional.ifPresent(billStatistics -> {
            backOrderInfo.put("count", billStatistics.getTotalCount());
            backOrderInfo.put("coin", billStatistics.getTotalCoin());
            vo.setBackOrderInfo(backOrderInfo);
        });

        return vo;
    }

    /**
     * 计算统计时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static Map<String, String> calculateBillTime(String year, String month, String day) {
        String startTimeSuffix = " 00:00:00";
        String endTimeSuffix = " 23:59:59";
        Map<String, String> dateMap = new HashMap<>(2);
        //如果年-月-日都为空，默认查询今日数据
        if (StringUtils.isBlank(year) && StringUtils.isBlank(month) && StringUtils.isBlank(day)) {
            Calendar cal = Calendar.getInstance();
            //当前年
            int tempYear = cal.get(Calendar.YEAR);
            //当前月
            int tempMonth = cal.get(Calendar.MONTH) + 1;
            //获取某月最小天数
            int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateMap.put("startTime", tempYear + "-" + tempMonth + "-" + firstDay + startTimeSuffix);
            dateMap.put("endTime", tempYear + "-" + tempMonth + "-" + lastDay + endTimeSuffix);
        } else {
            if (StringUtils.isNotBlank(year)) {//年查询
                dateMap.put("startTime", year + "-01-01" + startTimeSuffix);
                dateMap.put("endTime", year + "-12-31" + endTimeSuffix);
            } else if (StringUtils.isNotBlank(month)) {//月查询
                //截取具体年份
                String tempYear = month.split("-")[0];
                //截取具体月份
                String tempMonth = month.split("-")[1];
                Calendar cal = Calendar.getInstance();
                //设置年份
                cal.set(Calendar.YEAR, Integer.parseInt(tempYear));
                //设置月份
                cal.set(Calendar.MONTH, Integer.parseInt(tempMonth) - 1);
                //获取某月最小天数
                int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
                //获取某月最大天数
                int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                dateMap.put("startTime", month + "-" + firstDay + startTimeSuffix);
                dateMap.put("endTime", month + "-" + lastDay + endTimeSuffix);
            } else if (StringUtils.isNotBlank(day)) {//日查询
                dateMap.put("startTime", day + startTimeSuffix);
                dateMap.put("endTime", day + endTimeSuffix);
                //年-月不为空，日为空，则代表查询本月
            }
        }
        return dateMap;
    }
}

package com.ryit.credituserserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditMessageDto;
import com.ryit.commons.entity.dto.CreditMessageStandardDto;
import com.ryit.commons.entity.pojo.CreditMessage;
import com.ryit.commons.entity.pojo.CreditMessageStandard;
import com.ryit.commons.entity.vo.CreditMessageStandardVo;
import com.ryit.commons.entity.vo.CreditMessageVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credituserserver.dao.CreditMessageMapper;
import com.ryit.credituserserver.dao.CreditMessageStandardMapper;
import com.ryit.credituserserver.dao.CreditUserMapper;
import com.ryit.credituserserver.feign.MessageFeignClient;
import com.ryit.credituserserver.service.ICreditMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreditMessageServiceImpl implements ICreditMessageService {

    @Autowired
    private CreditMessageMapper creditMessageMapper;

    @Autowired
    private CreditMessageStandardMapper creditMessageStandardMapper;

    @Resource
    private MessageFeignClient messageFeignClient;

    @Resource
    private CreditUserMapper creditUserMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBillBehavior(Long userId, CreditMessageStandardDto creditMessageStandardDto) {
        try {
            CreditMessageStandard po = new CreditMessageStandard().buildPo(userId, creditMessageStandardDto);
            int count = creditMessageStandardMapper.insertSelective(po);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("保存订单偏好信息失败");
        }
    }

    @Override
    public CreditMessageStandardVo queryBillBehavior(Long userId) {
        try {
            CreditMessageStandard creditMessageStandard = creditMessageStandardMapper.selectMessageStandard(userId);
            return new CreditMessageStandardVo().buildVo(creditMessageStandard);
        } catch (Exception e) {
            throw new CustomException("订单偏好设置查询失败");
        }
    }

    /**
     * 保存消息
     *
     * @return
     */
    @Override
    public Boolean saveMessage(List<CreditMessageDto> messageDtoList) {
        List<CreditMessage> po = new CreditMessage().buildPoList(messageDtoList);
        return creditMessageMapper.insertBatch( po) > 0 ? true : false;
    }

    /**
     * 修改消息状态
     *
     * @return
     * @author samphin
     * @date 2019-9-30 10:07:36
     */
    @Override
    public Boolean updateMessageReadStatus(Long userId, List<Long> ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("ids", ids);
        params.put("readStatus",true);
        return creditMessageMapper.updateMessageReadStatus(params) > 0 ? true : false;
    }


    @Override
    public List<CreditMessageVo> queryHistoryMessage(Long userId) {

        try {
            List<CreditMessage> creditMessageList = creditMessageMapper.selectHistoryMessage(userId);

            return CreditMessageVo.buildVoList(creditMessageList);
        } catch (Exception e) {
            throw new CustomException("查询历史消息失败", e);
        }
    }
}

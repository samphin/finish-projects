package com.ryit.creditcouponserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditActivityDto;
import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.entity.pojo.CreditActivity;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.vo.CreditActivityBannerVo;
import com.ryit.commons.entity.vo.CreditActivityVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.dao.CreditActivityMapper;
import com.ryit.creditcouponserver.feign.CreditUserFeignClient;
import com.ryit.creditcouponserver.feign.MessageFeignClient;
import com.ryit.creditcouponserver.service.ICreditActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CreditActivityServiceImpl implements ICreditActivityService {

    @Autowired
    private CreditActivityMapper creditActivityMapper;

    @Resource
    private MessageFeignClient messageFeignClient;

    @Resource
    private CreditUserFeignClient creditUserFeignClient;

    @Override
    public List<CreditActivityBannerVo> queryAllBannerActivity() {
        try {
            List<CreditActivity> poList = creditActivityMapper.selectVisibleActivity();
            return CreditActivityBannerVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("查询用户活动失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CreditActivityDto dto) {
        try {
            CreditActivity po = new CreditActivity().buildPo(dto);
            int count = 0;
            if (null == dto.getId()) {
                count = creditActivityMapper.insert(po);
                //活动创建成功后，给系统所有用户推送消息。
                List<CreditUser> users = creditUserFeignClient.queryAllNotAdminCreditUsers();
                PushMessageDto messageDto = new PushMessageDto();
                messageDto.setTitle(po.getTitle());
                messageDto.setContent(po.getPageUrl());
                messageDto.setUsers(users);
                //柚子抢单消息
                messageDto.setAppType("credit");
                messageFeignClient.push(messageDto);
            } else {
                count = creditActivityMapper.updateByPrimaryKey(po);
            }
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("保存用户活动失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        try {
            int count = creditActivityMapper.deleteByPrimaryKey(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("删除用户活动失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        try {
            int count = creditActivityMapper.deleteBatch(ids);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("批量删除用户活动失败");
        }
    }

    @Override
    public List<CreditActivityVo> queryAllByCondition(CreditActivityDto dto) {
        try {
            CreditActivity po = new CreditActivity().buildPo(dto);
            List<CreditActivity> poList = creditActivityMapper.selectAllByCondition(po);
            return CreditActivityVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("条件查询用户活动失败");
        }
    }

    @Override
    public CreditActivityVo queryOne(Long id) {
        try {
            CreditActivity po = creditActivityMapper.selectByPrimaryKey(id);
            return CreditActivityVo.buildVo(po);
        } catch (Exception e) {
            throw new CustomException("查询用户活动详情失败");
        }
    }
}
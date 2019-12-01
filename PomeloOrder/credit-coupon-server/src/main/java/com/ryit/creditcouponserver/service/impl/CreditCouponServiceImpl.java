package com.ryit.creditcouponserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditCouponQueryDto;
import com.ryit.commons.entity.dto.CreditGrantCouponDto;
import com.ryit.commons.entity.pojo.CreditCoupon;
import com.ryit.commons.entity.pojo.CreditCouponList;
import com.ryit.commons.entity.pojo.CreditCouponModule;
import com.ryit.commons.entity.vo.CreditCouponListVo;
import com.ryit.commons.entity.vo.CreditCouponVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.dao.CreditCouponMapper;
import com.ryit.creditcouponserver.dao.CreditCouponModuleMapper;
import com.ryit.creditcouponserver.service.ICreditCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreditCouponServiceImpl implements ICreditCouponService {

    @Autowired
    private CreditCouponMapper creditCouponMapper;

    @Autowired
    private CreditCouponModuleMapper creditCouponModuleMapper;

    @Override
    public Boolean drawCoupon(CreditGrantCouponDto couponDto) {
        try {
            //通过优惠券模版ID，获取优惠券信息
            CreditCouponModule creditCouponModule = creditCouponModuleMapper.selectCouponModuleById(couponDto.getModuleId());
            List<CreditCoupon> poList = new CreditCoupon().buildPoList(couponDto, creditCouponModule);
            return creditCouponMapper.insertBatch(poList) > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("领取优惠券失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean drawFreeOfChargeCoupon(Long userId) {
        try {
            CreditCoupon po = new CreditCoupon().buildPo(userId);
            return creditCouponMapper.insert(po) > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("用户注册领取免单优惠券失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCouponUseStatus(Long relationId) {
        try {
            int count = this.creditCouponMapper.updateCouponUseStatus(relationId);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("修改优惠券使用状态失败");
        }
    }

    @Override
    public Double queryDiscountCoin(Long relationId) {
        try {
            CreditCouponModule creditCouponModule = this.creditCouponModuleMapper.selectDiscountCoin(relationId);
            //如果值为空，则代表没有任何优惠券可使用，直接返回-2
            if (null == creditCouponModule) {
                return -2D;
            } else if (null != creditCouponModule.getType() && 1 == creditCouponModule.getType()) {
                //如果是免单券则返回-1
                return -1D;
            }
            return creditCouponModule.getDiscountCoin();
        } catch (Exception e) {
            throw new CustomException("查询优惠券优惠金额失败");
        }
    }

    @Override
    public List<CreditCouponVo> queryNoUseCouponList(Long userId, Double coin) {
        try {
            Map<String, Object> queryParam = new HashMap<>(2);
            queryParam.put("userId", userId);
            if (null != coin) {
                queryParam.put("coin", coin);
            }
            List<CreditCouponModule> poList = creditCouponModuleMapper.selectNoUseCoupon(queryParam);
            return CreditCouponVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("币筛选优惠券列表失败");
        }
    }

    @Override
    public List<CreditCouponVo> queryPastDueCouponList(Long userId) {
        try {
            List<CreditCouponModule> poList = creditCouponModuleMapper.selectPastDueCoupon(userId);
            return CreditCouponVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("查询已过期的优惠券失败");
        }
    }

    @Override
    public List<CreditCouponListVo> queryCouponList(CreditCouponQueryDto queryDto) {
        try {
            Map<String, Object> queryParam = new HashMap<>(3);
            queryParam.put("moduleName", queryDto.getModuleName());
            queryParam.put("userName", queryDto.getUserName());
            queryParam.put("useStatus", queryDto.getUseStatus());
            List<CreditCouponList> poList = creditCouponMapper.selectCouponList(queryParam);
            return CreditCouponListVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("查询优惠券使用信息列表失败");
        }
    }
}

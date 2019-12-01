package com.ryit.creditcouponserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditCouponModuleDto;
import com.ryit.commons.entity.pojo.CreditCouponModule;
import com.ryit.commons.entity.vo.CreditCouponModuleVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.dao.CreditCouponModuleMapper;
import com.ryit.creditcouponserver.service.ICreditCouponModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优惠券业务层
 *
 * @author samphin
 * @date 2019-9-4 15:41:30
 */
@Service
public class CreditCouponModuleServiceImpl implements ICreditCouponModuleService {

    @Autowired
    private CreditCouponModuleMapper creditCouponModuleMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CreditCouponModuleDto dto) {
        try {
            CreditCouponModule po = new CreditCouponModule().buildPo(dto);
            int count = 0;
            if (null == dto.getId()) {
                count = creditCouponModuleMapper.insert(po);
            } else {
                count = creditCouponModuleMapper.updateByPrimaryKey(po);
            }
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("保存优惠券模版失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        try {
            int count = creditCouponModuleMapper.deleteByPrimaryKey(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("删除优惠券模板失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        try {
            int count = creditCouponModuleMapper.deleteBatch(ids);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("批量删除优惠券模板失败");
        }
    }

    @Override
    public List<CreditCouponModuleVo> queryAllByCondition(CreditCouponModuleDto dto) {
        try {
            CreditCouponModule po = new CreditCouponModule().buildPo(dto);
            List<CreditCouponModule> poList = creditCouponModuleMapper.selectAllByCondition(po);
            return CreditCouponModuleVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("条件查询优惠券失败");
        }
    }

    @Override
    public CreditCouponModuleVo queryOne(Long id) {
        try {
            CreditCouponModule po = creditCouponModuleMapper.selectByPrimaryKey(id);
            return CreditCouponModuleVo.buildVo(po);
        } catch (Exception e) {
            throw new CustomException("查询优惠券详情失败");
        }
    }

    @Override
    public boolean queryCouponCrawRecord(Long id) {
        try {
            Integer count = creditCouponModuleMapper.selectCouponCrawRecord(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("查询优惠券领取状态失败");
        }
    }
}

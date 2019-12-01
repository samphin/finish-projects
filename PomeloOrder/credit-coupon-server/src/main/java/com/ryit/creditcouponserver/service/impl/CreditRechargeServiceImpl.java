package com.ryit.creditcouponserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditRechargeDto;
import com.ryit.commons.entity.pojo.CreditRecharge;
import com.ryit.commons.entity.vo.CreditRechargeVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.dao.CreditRechargeMapper;
import com.ryit.creditcouponserver.service.ICreditRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 充值方案业务类
 *
 * @author samphin
 * @date 2019-8-28 16:35:13
 */
@Service
public class CreditRechargeServiceImpl implements ICreditRechargeService {

    @Autowired
    private CreditRechargeMapper creditRechargeMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CreditRechargeDto dto) {
        CreditRecharge po = new CreditRecharge().buildPo(dto);
        int count = 0;
        if (null == dto.getId()) {
            //校验充值金额
            int isExistMoney = creditRechargeMapper.selectRechargeByMoney(po);
            if (isExistMoney > 0) {
                throw new CustomException("充值方案已存在");
            }
            count = creditRechargeMapper.insert(po);
        } else {
            count = creditRechargeMapper.updateByPrimaryKey(po);
        }
        return count > 0 ? true : false;
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        try {
            int count = creditRechargeMapper.deleteByPrimaryKey(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("删除充值方案失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        try {
            int count = creditRechargeMapper.deleteBatch(ids);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("批量删除充值方案失败");
        }
    }

    @Override
    public List<CreditRechargeVo> queryAllByCondition(CreditRechargeDto dto) {
        try {
            CreditRecharge po = new CreditRecharge().buildPo(dto);
            List<CreditRecharge> poList = creditRechargeMapper.selectAllByCondition(po);
            return CreditRechargeVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("条件查询充值方案失败");
        }
    }

    @Override
    public CreditRechargeVo queryOne(Long id) {
        try {
            CreditRecharge po = creditRechargeMapper.selectByPrimaryKey(id);
            return CreditRechargeVo.buildVo(po);
        } catch (Exception e) {
            throw new CustomException("查询充值方案详情失败");
        }
    }
}

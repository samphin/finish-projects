package com.ryit.creditcouponserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditInviteDto;
import com.ryit.commons.entity.pojo.CreditInvite;
import com.ryit.commons.entity.vo.CreditInviteVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.dao.CreditInviteMapper;
import com.ryit.creditcouponserver.service.ICreditInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 邀请方案业务类
 *
 * @author samphin
 * @date 2019-9-3 12:02:57
 */
@Service
public class CreditInviteServiceImpl implements ICreditInviteService {

    @Autowired
    private CreditInviteMapper creditInviteMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CreditInviteDto dto) {
        try {
            CreditInvite po = new CreditInvite().buildPo(dto);
            int count = 0;
            if (null == dto.getId()) {
                count = creditInviteMapper.insert(po);
            } else {
                count = creditInviteMapper.updateByPrimaryKey(po);
            }
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("保存柚子抢单邀请方案管理失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        try {
            int count = creditInviteMapper.deleteByPrimaryKey(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("删除柚子抢单邀请方案管理失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        try {
            int count = creditInviteMapper.deleteBatch(ids);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("批量删除柚子抢单邀请方案管理失败");
        }
    }

    @Override
    public List<CreditInviteVo> queryAllByCondition(CreditInviteDto dto) {
        try {
            CreditInvite po = new CreditInvite().buildPo(dto);
            List<CreditInvite> poList = creditInviteMapper.selectAllByCondition(po);
            return CreditInviteVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("条件查询柚子抢单邀请方案管理失败");
        }
    }

    @Override
    public CreditInviteVo queryOne(Long id) {
        try {
            CreditInvite po = creditInviteMapper.selectByPrimaryKey(id);
            return CreditInviteVo.buildVo(po);
        } catch (Exception e) {
            throw new CustomException("查询柚子抢单邀请方案管理详情失败");
        }
    }

    @Override
    public boolean validateNum(Integer num) {
        try {
            int count = creditInviteMapper.validateNum(num);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("校验邀请人数失败");
        }
    }
}

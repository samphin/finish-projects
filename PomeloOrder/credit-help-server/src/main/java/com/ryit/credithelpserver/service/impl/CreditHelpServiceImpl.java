package com.ryit.credithelpserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditHelpDto;
import com.ryit.commons.entity.pojo.CreditHelp;
import com.ryit.commons.entity.vo.CreditHelpVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credithelpserver.dao.CreditHelpMapper;
import com.ryit.credithelpserver.service.ICreditHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditHelpServiceImpl implements ICreditHelpService {

    @Autowired
    private CreditHelpMapper helpMapper;


    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CreditHelpDto dto) {
        try {
            CreditHelp po = new CreditHelp().buildPo(dto);
            int count = 0;
            if (null == po.getId()) {
                count = helpMapper.insert(po);
            } else {
                count = helpMapper.updateByPrimaryKey(po);
            }
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("保存帮助管理失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        try {
            int count = helpMapper.deleteByPrimaryKey(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("删除帮助管理失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        try {
            int count = helpMapper.deleteBatch(ids);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("批量删除帮助管理失败");
        }
    }

    @Override
    public List<CreditHelpVo> queryAllByCondition(CreditHelpDto dto) {
        try {
            CreditHelp po = new CreditHelp().buildPo(dto);
            List<CreditHelp> poList = this.helpMapper.selectAllByCondition(po);
            return CreditHelpVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("条件查询帮助管理失败");
        }
    }

    @Override
    public CreditHelpVo queryOne(Long id) {
        try {
            CreditHelp po = helpMapper.selectByPrimaryKey(id);
            return CreditHelpVo.buildVo(po);
        } catch (Exception e) {
            throw new CustomException("查询帮助管理详情失败");
        }
    }
}

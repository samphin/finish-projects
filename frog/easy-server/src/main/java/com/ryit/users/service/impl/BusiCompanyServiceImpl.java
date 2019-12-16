package com.ryit.users.service.impl;

import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.entity.dto.BusiCompanyDto;
import com.ryit.commons.entity.pojo.BusiCompany;
import com.ryit.commons.entity.vo.BusiCompanyVo;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.users.mapper.BusiCompanyMapper;
import com.ryit.users.service.IBusiCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class BusiCompanyServiceImpl extends BaseServiceImpl<Long, BusiCompanyDto, BusiCompanyVo> implements IBusiCompanyService {

    @Autowired
    private BusiCompanyMapper busiCompanyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(BusiCompanyDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        BusiCompany po = new BusiCompany().buildPo(dto);
        if (dto.getId() == null) {
            po.setId(SnowflakeIdWorker.generateId());
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());
            return busiCompanyMapper.insertSelective(po) > 0;
        } else {
            po.setLastUpdateUserId(userId);
            po.setLastUpdateDate(new Date());
            return busiCompanyMapper.updateByPrimaryKeySelective(po) > 0;
        }
    }

    /**
     * 根据用户ID，查询其公司信息
     *
     * @param userId
     * @return
     */
    @Override
    public BusiCompanyVo queryCompanyInfoByUserId(Integer userId) {
        Example example = new Example(BusiCompany.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("createUserId", userId);
        BusiCompany po = busiCompanyMapper.selectOneByExample(example);
        BusiCompanyVo vo = new BusiCompanyVo().buildVo(po);
        return vo;
    }
}

package com.ryit.orderserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.SysApplicationDto;
import com.ryit.commons.entity.pojo.SysApplication;
import com.ryit.commons.entity.vo.SysApplicationVersionVo;
import com.ryit.commons.entity.vo.SysApplicationVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.dao.SysApplicationMapper;
import com.ryit.orderserver.service.ISysApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysApplicationServiceImpl implements ISysApplicationService {

    @Autowired
    private SysApplicationMapper sysApplicationMapper;

    /**
     * 根据APP编码获取APP信息
     *
     * @param code
     * @return
     */
    @Override
    public SysApplicationVersionVo queryByCode(String code) {
        SysApplication po = sysApplicationMapper.selectByCode(code);
        return SysApplicationVersionVo.buildVo(po);
    }


    @Override
    @LcnTransaction
    @Transactional
    public boolean save(SysApplicationDto dto) {
        SysApplication po = new SysApplication().buildPo(dto);
        if (null == po.getId()) {
            return sysApplicationMapper.insertSelective(po) > 0 ? true : false;
        } else {
            return sysApplicationMapper.updateByPrimaryKeySelective(po) > 0 ? true : false;
        }
    }

    @Override
    public boolean save(Long account, SysApplicationDto dto) {
        dto.setCreateUserId(account);
        Integer count = sysApplicationMapper.selectByVersion(dto.getVersion());
        if (count > 1) {
            throw new CustomException("版本号已存在，请重新填写");
        }

        String maxVersion = sysApplicationMapper.selectMaxVersion(dto.getCode());
        //如果数据库中版本号大于手填的版本号则提示，版本号过低
        if (null!= maxVersion && maxVersion.compareTo(dto.getVersion()) == 1) {
            throw new CustomException("当前版本号：" + dto.getVersion() + "过低，请填写更大版本号");
        }
        return save(dto);
    }

    @Override
    public boolean delete(Long id) {
        return sysApplicationMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean deleteBatch(List<Long> ids) {
        return false;
    }

    @Override
    public List<SysApplicationVo> queryAllByCondition(SysApplicationDto dto) {
        SysApplication po = new SysApplication().buildPo(dto);
        List<SysApplication> poList = sysApplicationMapper.selectAllByCondition(po);
        return SysApplicationVo.buildVoList(poList);
    }

    @Override
    public SysApplicationVo queryOne(Long id) {
        SysApplication po = sysApplicationMapper.selectByPrimaryKey(id);
        return SysApplicationVo.buildVo(po);
    }
}

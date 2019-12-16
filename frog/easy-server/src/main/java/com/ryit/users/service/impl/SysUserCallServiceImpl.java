package com.ryit.users.service.impl;

import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.entity.dto.SysUserCallInfoDto;
import com.ryit.commons.entity.pojo.SysUserCallInfo;
import com.ryit.commons.entity.vo.SysUserCallInfoListVo;
import com.ryit.commons.entity.vo.SysUserCallInfoVo;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.users.mapper.SysUserCallInfoMapper;
import com.ryit.users.service.ISysUserCallService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserCallServiceImpl extends BaseServiceImpl<Long, SysUserCallInfoDto, SysUserCallInfoListVo> implements ISysUserCallService {

    @Resource
    private SysUserCallInfoMapper sysUserCallInfoMapper;

    @Override
    public boolean insertSelective(SysUserCallInfoDto dto) {
        SysUserCallInfo po = new SysUserCallInfo().buildPo(dto);
        po.setId(SnowflakeIdWorker.generateId());
        po.setCreateDate(new Date());
        po.setTelephone(dto.getTelephone());
        po.setRealName(dto.getRealName());
        return sysUserCallInfoMapper.insertSelective(po) > 0;
    }

    @Override
    public SysUserCallInfoVo queryCallRecord(SysUserCallInfoDto dto) {
        SysUserCallInfoVo vo = new SysUserCallInfoVo();
        SysUserCallInfo po = new SysUserCallInfo().buildPo(dto);
        Example example = new Example(SysUserCallInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(dto.getRealName())) {
            criteria.andLike("realName", "%" + dto.getRealName() + "%");
        }
        if (StringUtils.isNotBlank(dto.getTelephone())) {
            criteria.andLike("telephone", "%" + dto.getTelephone() + "%");
        }
        example.orderBy("createDate").desc();
        List<SysUserCallInfo> poList = sysUserCallInfoMapper.selectByExample(example);

        List<SysUserCallInfoListVo> voList = new SysUserCallInfoListVo().buildVoList(poList);
        vo.setDataList(voList);
        vo.setTimes(voList.size());
        return vo;
    }
}

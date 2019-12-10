package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.SysUserCallInfoDto;
import com.ryit.commons.entity.vo.SysUserCallInfoListVo;
import com.ryit.commons.entity.vo.SysUserCallInfoVo;

public interface ISysUserCallService extends IBaseService<Long, SysUserCallInfoDto, SysUserCallInfoListVo> {

    /**
     * 查询回访记录
     *
     * @param dto
     * @return
     */
    SysUserCallInfoVo queryCallRecord(SysUserCallInfoDto dto);
}

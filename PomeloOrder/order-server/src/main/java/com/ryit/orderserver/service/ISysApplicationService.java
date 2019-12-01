package com.ryit.orderserver.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.SysApplicationDto;
import com.ryit.commons.entity.vo.SysApplicationVersionVo;
import com.ryit.commons.entity.vo.SysApplicationVo;

public interface ISysApplicationService extends IBaseService<Long, SysApplicationDto, SysApplicationVo> {

    /**
     * 根据APP编码获取APP版本相关信息
     *
     * @param code
     * @return
     */
    SysApplicationVersionVo queryByCode(String code);

    /**
     * 新增应用版本信息
     *
     * @param account
     * @param dto
     * @return
     */
    boolean save(Long account, SysApplicationDto dto);

}

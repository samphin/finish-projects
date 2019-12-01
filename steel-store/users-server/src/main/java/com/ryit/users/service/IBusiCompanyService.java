package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.BusiCompanyDto;
import com.ryit.commons.entity.vo.BusiCompanyVo;

public interface IBusiCompanyService extends IBaseService<Long, BusiCompanyDto, BusiCompanyVo> {

    /**
     * 根据用户ID，查询其公司信息
     * @param userId
     * @return
     */
    BusiCompanyVo queryCompanyInfoByUserId(Integer userId);
}

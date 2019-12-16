package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.BusiAdvertDto;
import com.ryit.commons.entity.pojo.BusiAdvert;
import com.ryit.commons.entity.vo.BusiAdvertVo;
import com.ryit.commons.entity.vo.BusiHomePageAdvertVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IBusiAdvertService extends IBaseService<Long, BusiAdvertDto, BusiAdvertVo> {

    /**
     * APP首页广告信息
     *
     * @return
     */
    List<BusiHomePageAdvertVo> queryAdvertInfo();

}
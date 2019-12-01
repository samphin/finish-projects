package com.ryit.creditcouponserver.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.CreditActivityDto;
import com.ryit.commons.entity.vo.CreditActivityBannerVo;
import com.ryit.commons.entity.vo.CreditActivityVo;

import java.util.List;

public interface ICreditActivityService extends IBaseService<Long, CreditActivityDto, CreditActivityVo> {

    /**
     * 查询所有允许显示的banner活动列表
     * @author samphin
     * @date 2019-8-30 09:39:19
     * @return
     */
    List<CreditActivityBannerVo> queryAllBannerActivity();
}
package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.BusiMembershipDto;
import com.ryit.commons.entity.vo.BusiMemberOrderVo;
import com.ryit.commons.entity.vo.BusiMembershipVo;

import java.util.List;

public interface IBusiMembershipService extends IBaseService<Long, BusiMembershipDto, BusiMembershipVo> {


    /**
     * 查询会员详情
     *
     * @param userId
     * @return
     */
    BusiMembershipVo queryMemberDetail(Integer userId);

    /**
     * 查询会员订单数量
     *
     * @return
     */
    List<BusiMemberOrderVo> queryMemberOrderNum();

}
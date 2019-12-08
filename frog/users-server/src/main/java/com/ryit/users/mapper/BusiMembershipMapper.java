package com.ryit.users.mapper;

import com.ryit.commons.entity.dto.BusiMembershipDto;
import com.ryit.commons.entity.pojo.BusiMembership;
import com.ryit.commons.entity.vo.BusiMemberOrderVo;
import com.ryit.commons.entity.vo.BusiMembershipVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiMembershipMapper extends Mapper<BusiMembership> {

    /**
     * 查询会员列表信息
     *
     * @author samphin
     * @since 2019-10-31 09:05:13
     */
    List<BusiMembershipVo> selectMemberList(BusiMembershipDto dto);

    /**
     * 根据用户编号查询会员信息
     *
     * @author samphin
     * @since 2019-10-31 09:05:13
     */
    BusiMembershipVo selectMemberDetail(@Param("userId") Integer userId);

    /**
     * 查询会员订单数量
     *
     * @return
     */
    List<BusiMemberOrderVo> selectMemberOrderNum();
}
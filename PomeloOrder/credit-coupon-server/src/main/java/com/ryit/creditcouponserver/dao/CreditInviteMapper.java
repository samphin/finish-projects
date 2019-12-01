package com.ryit.creditcouponserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.CreditInvite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditInviteMapper extends BaseMapper<Long, CreditInvite> {

    /**
     * 校验邀请人数据是否存在
     * @param num
     * @author samphin
     * @date 2019-9-9 15:05:12
     */
    Integer validateNum(@Param("num")Integer num);
}
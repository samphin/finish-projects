package com.ryit.creditcouponserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.CreditCoupon;
import com.ryit.commons.entity.pojo.CreditCouponList;
import com.ryit.commons.entity.vo.CreditCouponListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CreditCouponMapper extends BaseMapper<Integer,CreditCoupon> {

    /**
     * 根据主键ID，将当前绑定优惠券状态改为已使用
     *
     * @param relationId
     * @return
     * @author samphin
     * @date 2019-8-31 09:34:17
     */
    int updateCouponUseStatus(@Param("relationId") Long relationId);

    /**
     * 查询优惠券使用信息列表
     *
     * @param map
     * @author samphin
     * @date 2019-9-4 15:24:00
     */
    List<CreditCouponList> selectCouponList(Map map);
}
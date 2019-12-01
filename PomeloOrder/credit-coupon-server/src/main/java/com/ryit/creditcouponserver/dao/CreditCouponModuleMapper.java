package com.ryit.creditcouponserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.CreditCouponModule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CreditCouponModuleMapper extends BaseMapper<Long, CreditCouponModule> {

    /**
     * 根据优惠券模版ID，查询优惠券信息
     *
     * @param moduleId
     * @return
     * @author samphin
     * @date 2019-8-31 10:03:57
     */
    CreditCouponModule selectCouponModuleById(@Param("moduleId") long moduleId);

    /**
     * 查询用户未使用且未过期的优惠券
     *
     * @return
     * @author samphin
     * @date 2019-8-30 09:59:42
     */
    List<CreditCouponModule> selectNoUseCoupon(Map<String, Object> param);

    /**
     * 查询用户未使用且未过期的优惠券
     *
     * @param userId
     * @return
     * @author samphin
     * @date 2019-8-30 09:59:42
     */
    List<CreditCouponModule> selectPastDueCoupon(@Param("userId") Long userId);

    /**
     * 根据用户与优惠券关联表主键ID，获取当前优惠券优惠金额
     *
     * @param relationId
     * @return
     * @author samphin
     * @date 2019-8-31 10:21:23
     */
    CreditCouponModule selectDiscountCoin(@Param("relationId") Long relationId);

    /**
     * 查询优惠券模版是否被领取
     * @param id
     * @author samphin
     * @date 2019-9-9 10:56:51
     */
    Integer selectCouponCrawRecord(@Param("id")Long id);
}
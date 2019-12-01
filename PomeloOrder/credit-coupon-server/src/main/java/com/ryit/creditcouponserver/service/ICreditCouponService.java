package com.ryit.creditcouponserver.service;

import com.ryit.commons.entity.dto.CreditCouponDto;
import com.ryit.commons.entity.dto.CreditCouponQueryDto;
import com.ryit.commons.entity.dto.CreditGrantCouponDto;
import com.ryit.commons.entity.vo.CreditCouponListVo;
import com.ryit.commons.entity.vo.CreditCouponVo;

import java.util.List;

/**
 * 优惠券业务层
 *
 * @author samphin
 * @date 2019-8-28 16:35:13
 */
public interface ICreditCouponService {

    /**
     * 领取优惠券
     *
     * @author samphin
     * @date 2019-8-30 10:54:02
     */
    Boolean drawCoupon(CreditGrantCouponDto couponDto);

    /**
     * 用户注册领取免单优惠券
     *
     * @author samphin
     * @date 2019-8-30 10:54:02
     */
    Boolean drawFreeOfChargeCoupon(Long userId);

    /**
     * 根据用户与优惠券关联表主键ID将当前优惠券改成已使用状态
     *
     * @param relationId
     * @return
     * @author samphin
     * @date 2019-8-31 09:27:52
     */
    Boolean updateCouponUseStatus(Long relationId);

    /**
     * 根据用户与优惠券关联表主键ID，查询优惠券优惠金额（免单券：0，满减券：discountCoin具体值，没有则为-1）
     *
     * @param relationId
     * @return
     * @author samphin
     * @date 2019-8-31 09:30:35
     */
    Double queryDiscountCoin(Long relationId);

    /**
     * 根据用户ID或者抢单币筛选优惠券列表
     *
     * @param userId
     * @param coin
     * @return
     * @author samphin
     * @date 2019-8-30 10:54:15
     */
    List<CreditCouponVo> queryNoUseCouponList(Long userId, Double coin);

    /**
     * 查询已过期的优惠券
     *
     * @param userId
     * @return
     * @author samphin
     * @date 2019-8-30 10:54:59
     */
    List<CreditCouponVo> queryPastDueCouponList(Long userId);

    /**
     * 查询优惠券使用信息列表
     *
     * @param queryDto
     * @author samphin
     * @date 2019-9-4 15:24:00
     */
    List<CreditCouponListVo> queryCouponList(CreditCouponQueryDto queryDto);

}

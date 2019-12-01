package com.ryit.creditcouponserver.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.CreditCouponModuleDto;
import com.ryit.commons.entity.vo.CreditCouponModuleVo;

/**
 * 优惠券业务层
 *
 * @author samphin
 * @date 2019-9-4 15:41:30
 */
public interface ICreditCouponModuleService extends IBaseService<Long, CreditCouponModuleDto, CreditCouponModuleVo> {

    /**
     * 查询优惠券模版是否被领取过
     * @param id
     * @author samphin
     * @date 2019-9-9 10:59:24
     */
    boolean queryCouponCrawRecord(Long id);

}

package com.ryit.creditcouponserver.checktor;

import com.ryit.commons.entity.dto.CreditGrantCouponDto;
import com.ryit.commons.web.exception.user.CustomException;
import org.springframework.util.CollectionUtils;

/**
 * 发放优惠券校验器
 *
 * @author samphin
 * @date 2019-10-18 10:26:16
 */
public class CreditGrantCouponDtoChecktor {

    public static void check(CreditGrantCouponDto dto) {
        if (null == dto) {
            throw new CustomException("信息不能为空");
        }

        if(CollectionUtils.isEmpty(dto.getUserIds())){
            throw new CustomException("请选择信贷员");
        }

        if(null == dto.getModuleId()){
            throw new CustomException("请选择要发放的优惠券");
        }

    }
}

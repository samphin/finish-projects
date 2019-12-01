package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 发放优惠券Dto
 *
 * @author samphin
 * @date 2019-10-18 10:23:51
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditGrantCouponDto implements Serializable {

    private static final long serialVersionUID = 6320609232432511373L;

    /**
     * 优惠券模版ID
     */
    private Long moduleId;

    /**
     * 所属用户ID
     */
    private List<Long> userIds;


}

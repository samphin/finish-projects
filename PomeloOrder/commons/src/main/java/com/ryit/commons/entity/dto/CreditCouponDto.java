package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户优惠券关联对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponDto implements Serializable {

    private static final long serialVersionUID = -263176742518961967L;

    /**
     * 所属用户ID
     */
    private Long account;

    /**
     * 优惠券模版ID
     */
    private Long moduleId;
}

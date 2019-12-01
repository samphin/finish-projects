package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券模版对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponModuleDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 2346313609593057270L;

    /**
     * 优惠券模版ID
     */
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券面值（如果满减券则为满金额，条件值）
     */
    private Double coin;

    /**
     * 优惠金额面值（针对满减券，折扣值）
     */
    private Double discountCoin;

    /**
     * 优惠券类型:1：免单券，2：满减券
     */
    private Integer type;

    /**
     * 有效期起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;

    /**
     * 有效期结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
}

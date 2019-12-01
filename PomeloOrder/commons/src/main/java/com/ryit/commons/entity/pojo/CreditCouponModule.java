package com.ryit.commons.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryit.commons.entity.dto.CreditCouponModuleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券模版对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponModule implements Serializable {

    private static final long serialVersionUID = -6505839221801607824L;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartTime;

    /**
     * 有效期结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndTime;

    public CreditCouponModule buildPo(CreditCouponModuleDto dto) {
        if (null == dto) {
            return this;
        }
        BeanUtils.copyProperties(dto, this);
        return this;
    }
}

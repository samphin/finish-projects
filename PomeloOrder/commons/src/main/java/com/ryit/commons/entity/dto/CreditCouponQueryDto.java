package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户优惠券关联表查询参数封装类
 *
 * @author samphin
 * @date 2019-9-4 15:36:18
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponQueryDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 243297325169293241L;

    private String userName;

    private String moduleName;

    private Integer useStatus;
}

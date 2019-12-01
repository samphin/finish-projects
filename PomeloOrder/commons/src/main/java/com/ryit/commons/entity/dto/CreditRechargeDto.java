package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : samphin
 * @Date : 2019-9-3 11:57:40
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditRechargeDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 5164595257767175121L;

    private Long id;

    /**
     * 充值金额
     */
    private Double money;

    /**
     * 赠送金额
     */
    private Double gift;

    /**
     * 充值方案(0:首充1:重复充值)
     */
    private Integer rechargeType;
}

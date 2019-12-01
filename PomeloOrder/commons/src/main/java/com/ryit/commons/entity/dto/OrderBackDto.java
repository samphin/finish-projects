package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 退单退单对象
 *
 * @author : samphin
 * @Date : 2019-9-3 20:52:15
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderBackDto {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 是否通过 0：通过;1：不通过
     */
    private Integer isPass;

    /**
     * 不通时，退单失败原因
     */
    private String errorReason;
}

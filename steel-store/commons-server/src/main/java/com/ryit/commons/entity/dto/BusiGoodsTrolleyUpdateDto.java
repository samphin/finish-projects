package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改购物车信息
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
@ApiModel(value = "修改购物车Dto类")
public class BusiGoodsTrolleyUpdateDto implements Serializable {

    private Long id;

    /**
     * 规格数量
     */
    @ApiModelProperty(value = "规格数量")
    private Integer amount;

    /**
     * 规格合计总价
     */
    @ApiModelProperty(value = "规格合计总价")
    private Double totalPrice;
}
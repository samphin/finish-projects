package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 统一提交订单类
 *
 * @author samphin
 * @since 2019-10-21 17:45:53
 */
@Data
@ApiModel(value = "统一提交订单")
public class BusiSubmitOrdersDto implements Serializable {

    private static final long serialVersionUID = -7765965191881638048L;

    /**
     * 是否门店自提(0-否，1-是)
     */
    @ApiModelProperty(value = "是否门店自提(0-否，1-是)", allowableValues = "0,1")
    private Integer pickinstoreType;

    /**
     * 收货人地址ID
     */
    @ApiModelProperty(value = "收货人地址ID")
    private Long deliveryId;

    /**
     * 物流公司id
     */
    @ApiModelProperty(value = "物流公司ID")
    private String logisticsId;

    /**
     * 物流code
     */
    @ApiModelProperty(value = "物流编码")
    private String logisticsCode;

    /**
     * 物流名称
     */
    @ApiModelProperty(value = "物流名称")
    private String logisticsName;

    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号")
    private String logisticsNo;

    /**
     * 优惠券面值
     */
    @ApiModelProperty(value = "优惠券面值")
    private Double couponPrice;

    /**
     * 应付款金额
     */
    @ApiModelProperty(value = "应付款金额")
    private Double orderAmount;

    /**
     * 订单总价
     */
    @ApiModelProperty(value = "订单总价")
    private Double totalAmount;

    /**
     * 价格调整
     */
    @ApiModelProperty(value = "价格调整")
    private Double discount;

    /**
     * 用户备注
     */
    @ApiModelProperty(value = "用户备注")
    private String userNote;

    /**
     * 商品规格集合
     */
    @NotEmpty(message = "商品规格信息不能为空")
    private List<BusiGoodsSkuDto> skuList;
}
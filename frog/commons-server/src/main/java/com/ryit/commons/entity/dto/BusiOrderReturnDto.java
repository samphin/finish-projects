package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 退货申请对象
 *
 * @author samphin
 * @since 2019-11-25 13:53:52
 */
@Data
@ApiModel(value = "订单退款信息")
public class BusiOrderReturnDto implements Serializable {

    private static final long serialVersionUID = -7829573008412383961L;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "退款订单id不能为空")
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    /**
     * 商品规格id
     */
    @ApiModelProperty(value = "商品规格ID")
    private Long skuId;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    private BigDecimal price;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer nums;

    /**
     * 0退货1换货
     */
    @ApiModelProperty(value = "类型 0退货,1换货")
    private Short type;

    /**
     * 退换货原因
     */
    @ApiModelProperty(value = "退换货原因")
    private String reason;

    /**
     * 退款说明
     */
    @ApiModelProperty(value = "说明")
    private String remark;

    /**
     * 拍照图片路径
     */
    @ApiModelProperty(value = "拍照路径(允许多张)")
    private List<String> imgs;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    /**
     * 0申请中1处理中2已完成
     */
    @ApiModelProperty(value = "退款订单状态")
    private Short status;

}
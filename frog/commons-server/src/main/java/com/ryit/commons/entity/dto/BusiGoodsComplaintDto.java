package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "商品投诉信息")
public class BusiGoodsComplaintDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = -3434586204621503026L;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品列表图标
     */
    @ApiModelProperty(name = "商品列表图标")
    private String goodsImgPath;

    /**
     * 规格ID
     */
    @ApiModelProperty(value = "商品规格ID")
    private Long skuId;

    /**
     * 长度(单位：米，精确到小数点后3位，四舍五入)
     */
    @ApiModelProperty(name = "商品规格长度")
    private Double length;

    /**
     * 宽度(单位：米，精确到小数点后3位，四舍五入)
     */
    @ApiModelProperty(name = "商品规格宽度")
    private Double width;

    /**
     * 厚度(单位：毫米)
     */
    @ApiModelProperty(name = "商品规格厚度")
    private Double thickness;

    /**
     * 重量（单位：吨，精确到小数点后3位，四舍五入）
     */
    @ApiModelProperty(name = "商品规格重量")
    private Double weight;

    /**
     * 数量
     */
    @ApiModelProperty(name = "商品规格数量")
    private Integer amount;

    /**
     * 产生金额
     */
    @ApiModelProperty(name = "商品规格产生金额")
    private Double totalPrice;

    /**
     * 投诉内容
     */
    @ApiModelProperty(value = "投诉内容")
    @NotBlank(message = "投诉内容不能为空", groups = {Save.class})
    private String complaintContent;

    /**
     * 处理说明
     */
    @ApiModelProperty(value = "处理说明")
    @NotBlank(message = "处理说明不能为空", groups = {Update.class})
    private String handlerExplain;
}
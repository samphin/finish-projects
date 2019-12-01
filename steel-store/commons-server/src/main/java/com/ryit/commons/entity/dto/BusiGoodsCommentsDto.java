package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品评价表
 *
 * @author samphin
 * @since 2019-10-21 17:43:15
 */
@Data
@ApiModel(value = "商品评价")
public class BusiGoodsCommentsDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = -5483470356212844304L;

    /**
     * 商品id
     */
    @ApiModelProperty(name = "商品id")
    private Long goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(name = "商品名称")
    private String goodsName;

    /**
     * 商品列表图标
     */
    @ApiModelProperty(name = "商品列表图标")
    private String goodsImgPath;

    /**
     * 规格id
     */
    @ApiModelProperty(name = "商品规格id")
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
     * 评分级别 总共5颗星
     */
    @ApiModelProperty(name = "评分级别 总共5颗星")
    private Double grade;

    /**
     * 评价内容
     */
    @ApiModelProperty(name = "评价内容")
    private String content;

    /**
     * 评价图片
     */
    private List<String> images;
}
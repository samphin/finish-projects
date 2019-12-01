package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品表
 *
 * @author samphin
 * @since 2019-10-21 17:41:50
 */
@Data
@ApiModel(value = "商品对象")
public class BusiGoodsDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 8120164515314057543L;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 标题
     */
    @ApiModelProperty(value = "商品标题")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "商品副标题")
    private String subhead;

    /**
     * 简介
     */
    @ApiModelProperty(value = "商品简介")
    private String description;

    /**
     * 成本价
     */
    @ApiModelProperty(value = "商品成本价")
    private Double costPrice;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "商品市场价")
    private Double marketPrice;

    /**
     * 促销价
     */
    @ApiModelProperty(value = "商品促销价")
    private Double discountPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "商品库存数量")
    private Integer stockNums;

    /**
     * 默认厚度
     */
    @ApiModelProperty(value = "商品默认厚度")
    private Integer defaultThickness;

    /**
     * 默认宽度（只针对横拉版）
     */
    @ApiModelProperty(value = "默认宽度（只针对横拉版）")
    private Double defaultWidth;

    /**
     * 是否推荐 0:否 1:是
     */
    @ApiModelProperty(value = "商品是否推荐->0:否 1:是", allowableValues = "0,1")
    private Integer recommendStatus;

    /**
     * 是否新品 0:否 1:是
     */
    @ApiModelProperty(value = "商品是否新品->0:否 1:是", allowableValues = "0,1")
    private Integer newStatus;

    /**
     * 是否热卖 0:否 1:是
     */
    @ApiModelProperty(value = "商品是否热卖->0:否 1:是", allowableValues = "0,1")
    private Integer hotStatus;

    /**
     * 是否包邮->0:否 1:是
     */
    @ApiModelProperty(value = "是否包邮->0:否 1:是", allowableValues = "0,1")
    private Integer freeShippingStatus;

    /**
     * 邮费
     */
    @ApiModelProperty(value = "邮费")
    private Double postagePrice;

    /**
     * 邮费说明
     */
    @ApiModelProperty(value = "邮费说明")
    private String postageDes;

    /**
     * 商品分类ID
     */
    @ApiModelProperty(value = "商品分类ID")
    private Long brandId;

    /**
     * 商品形状类型
     */
    @ApiModelProperty(value = "商品形状类型->1:方形板；2:异形板；3:横拉板", allowableValues = "1,2,3")
    private Integer shapeCode;

    /**
     * 商品列表图片
     */
    @ApiModelProperty(value = "商品列表图片")
    private String imgPath;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品缩略图片")
    private List<String> images;
}
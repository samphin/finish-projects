package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品列表查询参数类
 *
 * @author samphin
 * @since 2019-10-21 17:41:50
 */
@Data
@ApiModel(value = "PC端查询商品对象")
public class BusiGoodsPcQueryDto implements Serializable {

    private static final long serialVersionUID = 5898058021135201878L;

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
     * 排序
     */
    @ApiModelProperty(value = "商品排序")
    private Integer sort;

    /**
     * 厚度最小值
     */
    private Integer thicknessMin;

    /**
     * 厚度最大值
     */
    private Integer thicknessMax;

    /**
     * 是否推荐 0:否 1:是
     */
    private Integer recommendStatus;

    /**
     * 是否新品 0:否 1:是
     */
    private Integer newStatus;

    /**
     * 是否热卖 0:否 1:是
     */
    private Integer hotStatus;

    /**
     * 邮费说明
     */
    private String postageDes;

    /**
     * 邮费
     */
    private Double postagePrice;

    /**
     * 是否包邮0否1是
     */
    private Integer freeShippingStatus;

    /**
     * 分类ID
     */
    private Long brandId;

    /**
     * 商品形状类型
     */
    private Integer shapeCode;

    /**
     * 上架商品 0未发布 1上架 2下架
     */
    private Integer shelfStatus;

    /**
     * 商品列表图片
     */
    private String imgPath;

    /**
     * 访问数量
     */
    private Integer visit;

    /**
     * 点赞数
     */
    private Integer pointNum;

    /**
     * 收藏量
     */
    private Integer collectionNum;

    /**
     * 商品评价数
     */
    private Integer commentCount;

    /**
     * 销售总数
     */
    private Integer sale;

    /**
     * 1:有效 0失效
     */
    private Integer status;

    /**
     * 商品拼音
     */
    private String nameSpell;

    /**
     * 商品拼音首字母(简拼)
     */
    private String nameSimpleSpell;
}
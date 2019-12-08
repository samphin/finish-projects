package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoods;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品列表Vo类
 *
 * @author samphin
 * @since 2019-11-15 12:01:45
 */
@Data
public class BusiGoodsListVo extends BaseVo<Long, BusiGoods, BusiGoodsListVo> implements Serializable {

    private static final long serialVersionUID = 3604741771555278555L;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subhead;

    /**
     * 简介
     */
    private String description;

    /**
     * 成本价
     */
    private Double costPrice;

    /**
     * 市场价
     */
    private Double marketPrice;

    /**
     * 促销价
     */
    private Double discountPrice;

    /**
     * 库存数量
     */
    private Integer stockNums;

    /**
     * 默认厚度
     */
    private Integer defaultThickness;

    /**
     * 是否推荐 0:否 1:是
     */
    private Integer recommendStatus;

    /**
     * 是否新品 0:否 1:是
     */
    private Integer newStatus;

    /**
     * 上架商品 0未发布 1上架 2下架
     */
    private Integer shelfStatus;

    /**
     * 是否热卖 0:否 1:是
     */
    private Integer hotStatus;

    /**
     * 是否包邮->0:否 1:是
     */
    private Integer freeShippingStatus;

    /**
     * 邮费
     */
    private Double postagePrice;

    /**
     * 邮费说明
     */
    private String postageDes;

    /**
     * 商品分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long brandId;

    /**
     * 商品形状类型
     */
    private Integer shapeCode;

    /**
     * 商品列表图片
     */
    private String imgPath;
}
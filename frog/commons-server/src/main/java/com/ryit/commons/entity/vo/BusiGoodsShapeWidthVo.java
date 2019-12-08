package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品形状宽度类
 *
 * @author samphin
 * @since 2019-11-19 09:43:06
 */
@Data
public class BusiGoodsShapeWidthVo implements Serializable {

    private static final long serialVersionUID = 9068396512988852834L;

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
package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
public class BusiGoodsTrolleyDto implements Serializable {

    private static final long serialVersionUID = -3544845372478173091L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品列表图片
     */
    private String goodsImg;

    /**
     * 商品当前价格
     */
    private Integer goodsPrice;

    /**
     * 规格长度
     */
    private Double length;

    /**
     * 规格宽度
     */
    private Double width;

    /**
     * 规格厚度
     */
    private Double thickness;

    /**
     * 规格重量
     */
    private Double weight;

    /**
     * 规格数量
     */
    private Integer amount;

    /**
     * 规格合计总价
     */
    private Double totalPrice;
}
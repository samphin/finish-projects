package com.ryit.commons.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单商品列表
 *
 * @author samphin
 * @since 2019-11-22 09:18:09
 */
@Data
public class BusiOrderGoodsListVo implements Serializable {

    private static final long serialVersionUID = 4373742717263940597L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品列表图标
     */
    private String goodsImg;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 长度(单位：米，精确到小数点后3位，四舍五入)
     */
    private Double length;

    /**
     * 宽度(单位：米，精确到小数点后3位，四舍五入)
     */
    private Double width;

    /**
     * 厚度(单位：毫米)
     */
    private Double thickness;

    /**
     * 重量（单位：吨，精确到小数点后3位，四舍五入）
     */
    private Double weight;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 规格单价
     */
    private Double price;

    /**
     * 规格合计价
     */
    private Double totalPrice;

    /**
     * 是否评价(0:否；1:是)
     */
    private Integer commentStatus;

    /**
     * 0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠
     */
    private Integer activityType;

    /**
     * 0未发货，1已发货，2已换货，3已退货
     */
    private Integer sendStatus;

    /**
     * 0未提货，1已提货
     */
    private Integer pickStatus;

}

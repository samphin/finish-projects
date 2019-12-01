package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiOrderGoods;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;

@Data
public class BusiOrderGoodsVo extends BaseVo<Long, BusiOrderGoods,BusiOrderGoodsVo> implements Serializable {

    private static final long serialVersionUID = 744177647956172654L;

    /**
     * 门店id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orgId;

    /**
     * 订单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品规格id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 购买数量
     */
    private Integer goodsNum;

    /**
     * 本店价
     */
    private Double goodsPrice;

    /**
     * 提货码
     */
    private String pickCode;

    /**
     * 商品成本价
     */
    private Double costPrice;

    /**
     * 会员折扣价
     */
    private Double discountPrice;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 是否评价(0:否；1:是)
     */
    private Integer commentStatus;

    /**
     * 0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠
     */
    private Integer activityType;

    /**
     * 活动id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long activityId;

    /**
     * 0未发货，1已发货，2已换货，3已退货
     */
    private Integer sendStatus;

    /**
     * 0未提货，1已提货
     */
    private Integer pickStatus;

    /**
     * 发货单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deliveryId;

    private String imgPath;

    private Double length;

    private Double width;

    private Double thickness;

    private Double weight;

    private Double amount;
}

package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;

/**
 * 订单商品关联表
 *
 * @author samphin
 * @since 2019-10-21 17:46:26
 */
@Data
public class BusiOrderGoodsDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 2829004843982726389L;

    /**
     * 门店id
     */
    private Long orgId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品规格ID
     */
    private Long skuId;

    /**
     * 购买规格数量
     */
    private Integer skuNum;

    /**
     * 商品规格原价
     */
    private Double skuPrice;

    /**
     * 商品规格会员折扣价
     */
    private Double discountPrice;

    /**
     * 商品规格成本价
     */
    private Double skuCostPrice;

    /**
     * 提货码
     */
    private String pickCode;

    /**
     * 条形码
     */
    private String barCode;

    /**
     * 是否评价(0:否；1:是)
     */
    private Integer commentStatus;

    /**
     * 活动类型：0-普通订单,1-限时抢购, 2-团购 , 3-促销优惠
     */
    private Integer activityType;

    /**
     * 商品活动id
     */
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
    private Long deliveryId;
}
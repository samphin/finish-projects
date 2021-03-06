package com.ryit.commons.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.lang.Double;
import java.util.Date;
import java.util.List;

/**
 * 订单表
 *
 * @author samphin
 * @since 2019-10-21 17:45:53
 */
@Data
public class BusiOrderDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 5484891177664721016L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 门店id
     */
    private Long orgId;

    /**
     * 订单状态(0-待付款，1-待发货，2-待收货，3-已取消，4-已完成)
     */
    private Integer status;

    /**
     * 订单类型(0 普通,1 限时抢购,秒杀, 2 团购 , 3 促销优惠)
     */
    private Integer type;

    /**
     * 是否门店自提(0-否，1-是)
     */
    private Integer pickinstoreType;

    /**
     * 支付状态 （0-未支付，1-已支付）
     */
    private Integer payStatus;

    /**
     * 发货状态（0-未发货、1-已发货、2-部分发货）
     */
    private Integer logisticsStatus;

    /**
     * 最新发货时间
     */
    private Date logisticsTime;

    /**
     * 收货人地址ID
     */
    private Long deliveryId;

    /**
     * 物流公司id
     */
    private String logisticsId;

    /**
     * 物流code
     */
    private String logisticsCode;

    /**
     * 物流名称
     */
    private String logisticsName;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 支付code（alipay、weixin、cod）
     */
    private String payCode;

    /**
     * 支付方式名称（支付宝支付、微信支付、货到付款、现金支付）
     */
    private String payName;

    /**
     * 发票类型 0-个人，1-公司
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 税号
     */
    private String taxCode;

    /**
     * 邮箱地址(发票使用)
     */
    private String email;

    /**
     * 邮费
     */
    private Double postage;

    /**
     * 使用余额
     */
    private Double useMoney;

    /**
     * 优惠券抵扣
     */
    private Double couponPrice;

    /**
     * 应付款金额
     */
    private Double orderAmount;

    /**
     * 订单总价
     */
    private Double totalAmount;

    /**
     * 订单确认时间（后台）
     */
    private Date confirmTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 提货码
     */
    private String pickCode;

    /**
     * 活动id
     */
    private String orderPromId;

    /**
     * 活动优惠金额
     */
    private Double orderPromAmount;

    /**
     * 价格调整
     */
    private Double discount;

    /**
     * 用户备注
     */
    private String userNote;

    /**
     * 是否删除(0:未删除；1：已删除)
     */
    private Integer delStatus;

    /**
     * 是否提货(0未提货，1已提货)
     */
    private Integer pickStatus;

    /**
     * 订单金额起
     */
    private Double amount;


    /**
     * 商品ID
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
     * 商品规格集合
     */
    @NotEmpty(message = "商品规格不能为空", groups = BusiGoodsSkuDto.Save.class)
    private List<BusiGoodsSkuDto> skus;
}
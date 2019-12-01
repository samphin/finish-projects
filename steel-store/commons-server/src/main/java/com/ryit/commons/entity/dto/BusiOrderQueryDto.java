package com.ryit.commons.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单列表查询对象
 *
 * @author samphin
 * @since 2019-10-21 17:45:53
 */
@Data
public class BusiOrderQueryDto implements Serializable {

    private static final long serialVersionUID = 1438978742058702846L;

    /**
     * 订单编号
     */
    private String orderNo;

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
     * 发货状态（0-未发货、1-已发货、2-部分发货）
     */
    private Integer logisticsStatus;

    /**
     * 支付状态 （0-未支付，1-已支付）
     */
    private Integer payStatus;

    /**
     * 支付凭证状态：0-未上传 1-已上传
     */
    private Integer certificateStatus;

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
     * 应付款金额
     */
    private Double orderAmount;

    /**
     * 订单总价
     */
    private Double totalAmount;

    /**
     * 最新发货时间
     */
    private Date logisticsTime;

    /**
     * 订单确认时间
     */
    private Date confirmTime;

    /**
     * 支付时间
     */
    private Date payTime;

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
     * 创建人
     */
    private Integer createUserId;

    /**
     * 创建时间起
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date createStartDate;

    /**
     * 创建时间止
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date createEndDate;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 订单金额起
     */
    private Double amountMin;

    /**
     * 订单金额止
     */
    private Double amountMax;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;
}
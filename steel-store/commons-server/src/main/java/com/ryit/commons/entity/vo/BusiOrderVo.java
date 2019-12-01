package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiDeliveryAddress;
import com.ryit.commons.entity.pojo.BusiOrder;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;
import java.util.Date;

@Data
public class BusiOrderVo extends BaseVo<Long, BusiOrder, BusiOrderVo> implements Serializable {

    private static final long serialVersionUID = 5793182938001834502L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 门店id
     */
    private Integer orgId;

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
     * 收货人地址信息
     */
    private BusiDeliveryAddress deliveryAddress;

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
     * 支付方式名称（支付宝支付、微信支付、货到付款，现金支付）
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
     * 商品总价
     */
    private Double goodsPrice;

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
     * 最新发货时间
     */
    private Date newestShipmentDate;

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
     * 数据版本号
     */
    private Integer version;

    /***************操作记录信息 Start*****************/

    /**
     * 下单用户id
     */
    private Integer createUserId;

    /**
     * 下单时间
     */
    private Date createDate;

    /**
     * 修改人id
     */
    private Integer lastUpdateUserId;

    /**
     * 修改时间
     */
    private Date lastUpdateDate;

}

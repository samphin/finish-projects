package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单列表Vo类
 *
 * @author samphin
 * @since 2019-10-29 17:05:52
 */
@Data
public class BusiOrderDetailVo implements Serializable {

    private static final long serialVersionUID = 537971031766985013L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 支付方式名称（支付宝支付、微信支付、货到付款，现金支付）
     */
    private String payName;

    /**
     * 应付款金额
     */
    private Double orderAmount;

    /**
     * 订单总价
     */
    private Double totalAmount;

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
     * 最新发货时间
     */
    private Date logisticsTime;

    /**
     * 订单确认时间（后台）
     */
    private Date confirmTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 用户备注
     */
    private String userNote;

    /**
     * 是否提货(0未提货，1已提货)
     */
    private Integer pickStatus;

    /**
     * 下单时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date lastUpdateDate;

    /**
     * 订单商品列表
     */
    private List<BusiOrderGoodsListVo> orderGoods = new ArrayList<>();

    /**
     * 订单支付凭证
     */
    private List<String> certificates = new ArrayList<>();
}

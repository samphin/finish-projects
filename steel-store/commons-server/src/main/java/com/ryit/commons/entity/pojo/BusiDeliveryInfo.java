package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiDeliveryInfoDto;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;
import java.util.Date;

/**
 * 发货单
 *
 * @author samphin
 * @since 2019-10-21 17:41:04
 */
@Data
public class BusiDeliveryInfo extends BasePo<Long, BusiDeliveryInfoDto, BusiDeliveryInfo> implements Serializable {

    private static final long serialVersionUID = -8639638039244389550L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 收货人名称
     */
    private String consignee;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 收货人手机号码
     */
    private String mobilePhone;

    /**
     * 收货人所在国家
     */
    private String country;

    /**
     * 收货人所在省份
     */
    private String province;

    /**
     * 收货人所在城市
     */
    private String city;

    /**
     * 收货人所在区(县)
     */
    private String area;

    /**
     * 收货人所在详细地址
     */
    private String address;

    /**
     * 物流code
     */
    private String logisticsCode;

    /**
     * 快递名称
     */
    private String logisticsName;

    /**
     * 运费
     */
    private Double logisticsPrice;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 座机电话
     */
    private String telephone;

    /**
     * 管理员添加的备注信息
     */
    private String note;

    /**
     * 用户自定收货时间
     */
    private Date bestTime;

    /**
     * 是否删除(0:否；1:是)
     */
    private Integer delStatus;
}
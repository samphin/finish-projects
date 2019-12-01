package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiDeliveryAddress;
import lombok.Data;

import java.io.Serializable;

/**
 * 收货地址
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
public class BusiDeliveryAddressVo extends BaseVo<Long, BusiDeliveryAddress, BusiDeliveryAddressVo> implements Serializable {

    private static final long serialVersionUID = -4843097724118647202L;

    /**
     * 收货人名称
     */
    private String consignee;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区(县)
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipcode;

    /**
     * 是否默认收货地址->0-否；1-是
     */
    private Integer defaultStatus;

    /**
     * 地址标签
     */
    private String label;
}
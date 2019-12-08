package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiDeliveryAddressDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货地址
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
public class BusiDeliveryAddress extends BasePo<Long, BusiDeliveryAddressDto, BusiDeliveryAddress> implements Serializable {

    private static final long serialVersionUID = 1032728165367794009L;

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

    /***************操作记录信息 Start*****************/
    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改人ID
     */
    private Integer lastUpdateUserId;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;
}
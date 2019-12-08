package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 收货地址
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
@ApiModel(value = "收货地址对象信息")
public class BusiDeliveryAddressDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = -350171567698337301L;

    /**
     * 收货人名称
     */
    @ApiModelProperty(name = "收货人名称")
    @NotBlank(message = "收货人名称不能为空", groups = {Save.class, Update.class})
    private String consignee;

    /**
     * 收货人手机
     */
    @ApiModelProperty(name = "收货人手机号")
    @NotBlank(message = "手机号不能为空", groups = {Save.class, Update.class})
    private String mobilePhone;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(name = "收货人邮箱地址")
    private String email;

    /**
     * 国家
     */
    @ApiModelProperty(name = "国家")
    private String country;

    /**
     * 省份
     */
    @ApiModelProperty(name = "省份")
    @NotBlank(message = "省不能为空", groups = {Save.class, Update.class})
    private String province;

    /**
     * 城市
     */
    @ApiModelProperty(name = "城市")
    @NotBlank(message = "市不能为空", groups = {Save.class, Update.class})
    private String city;

    /**
     * 区(县)
     */
    @ApiModelProperty(name = "区(县)")
    @NotBlank(message = "区不能为空", groups = {Save.class, Update.class})
    private String area;

    /**
     * 详细地址
     */
    @ApiModelProperty(name = "详细地址")
    @NotBlank(message = "详细地址不能为空", groups = {Save.class, Update.class})
    private String address;

    /**
     * 邮政编码
     */
    @ApiModelProperty(name = "邮政编码")
    private String zipcode;

    /**
     * 是否默认收货地址->0-否；1-是
     */
    @ApiModelProperty(name = "是否默认收货地址->0-否；1-是")
    private Integer defaultStatus;

    /**
     * 地址标签
     */
    @ApiModelProperty(name = "地址标签")
    private String label;
}
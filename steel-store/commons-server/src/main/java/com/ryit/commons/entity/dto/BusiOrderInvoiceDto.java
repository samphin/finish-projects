package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单发票参数类
 *
 * @author samphin
 * @since 2019-11-21 17:44:20
 */
@Data
@ApiModel(value = "订单发票参数类")
public class BusiOrderInvoiceDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 660601229858158766L;

    /**
     * 发票类型 0-个人，1-公司
     */
    @ApiModelProperty(value = "发票类型0-个人，1-公司", allowableValues = "0,1")
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    private String invoiceTitle;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 税号
     */
    @ApiModelProperty(value = "公司税号")
    private String taxCode;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;
}
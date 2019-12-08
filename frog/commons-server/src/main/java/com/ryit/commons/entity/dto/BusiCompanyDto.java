package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 公司信息
 *
 * @author samphin
 * @since 2019-10-22 12:22:14
 */
@ApiModel(value = "公司信息")
@Data
public class BusiCompanyDto implements Serializable {

    private static final long serialVersionUID = -7316955522585323080L;
    
    private Long id;

    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 公司性质
     */
    @ApiModelProperty(value = "公司类型")
    private String companyType;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty(value = "纳税人识别号")
    @NotBlank(message = "纳税人识别号不能为空")
    private String taxpayerCode;

    /**
     * 公司地址
     */
    @ApiModelProperty(value = "公司地址")
    @NotBlank(message = "公司地址不能为空")
    private String address;

    /**
     * 公司电话
     */
    @ApiModelProperty(value = "公司电话")
    @NotBlank(message = "公司电话不能为空")
    private String telephone;

    /**
     * 账号
     */
    @ApiModelProperty(value = "公司账号")
    @NotBlank(message = "公司账号不能为空")
    private String account;

    /**
     * 开户行
     */
    @ApiModelProperty(value = "公司开户行")
    @NotBlank(message = "公司开户行不能为空")
    private String belongBank;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "公司联系人")
    @NotBlank(message = "公司联系人不能为空")
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "公司联系人")
    @NotBlank(message = "公司联系电话不能为空")
    private String contactsPhone;
}
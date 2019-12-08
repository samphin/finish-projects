package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "用户列表查询条件对象")
@Data
public class SysUserQueryDto implements Serializable {

    private static final long serialVersionUID = 7363784016883408320L;

    @ApiModelProperty(value = "登录名", notes = "登录名")
    private String username;

    @ApiModelProperty(value = "真实姓名", notes = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "性别", notes = "性别", allowableValues = "0,1,2")
    private int sex;

    @ApiModelProperty(value = "手机号码", notes = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "所在省份编码", notes = "所在省份编码")
    private String provinceCode;

    @ApiModelProperty(value = "所在城市编码", notes = "所在城市编码")
    private String cityCode;

    @ApiModelProperty(value = "所在区县编码", notes = "所在区县编码")
    private String districtCode;

    @ApiModelProperty(value = "身份证号码", notes = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "用户状态", notes = "用户状态", allowableValues = "1,2,3,4 ")
    private boolean state;
}

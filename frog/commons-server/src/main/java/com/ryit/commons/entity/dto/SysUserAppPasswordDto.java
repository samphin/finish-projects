package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "APP用户密码修改", description = "用作修改APP用户密码")
@Data
public class SysUserAppPasswordDto implements Serializable {

    private static final long serialVersionUID = 503998827285933200L;

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    @NotBlank(message = "手机号不能为空")
    private String mobilePhone;

    @NotBlank(message = "手机验证码")
    private String validateCode;

}

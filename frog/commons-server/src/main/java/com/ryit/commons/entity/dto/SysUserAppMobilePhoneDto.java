package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "APP用户手机号修改", description = "用作修改APP用户手机号")
@Data
public class SysUserAppMobilePhoneDto implements Serializable {

    private static final long serialVersionUID = 1110534321186298311L;

    @NotBlank(message = "手机号不能为空")
    private String newMobilePhone;

    @NotBlank(message = "手机验证码")
    private String validateCode;

}

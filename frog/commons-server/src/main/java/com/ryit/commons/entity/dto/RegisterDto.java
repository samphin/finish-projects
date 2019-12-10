package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 注册Dto
 *
 * @author samphin
 * @since 2019-12-10 20:38:46
 */
@Data
@ApiModel(value = "用户简单注册Dto")
public class RegisterDto implements Serializable {

    private static final long serialVersionUID = 60295740953737452L;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "回访时间段 1-随时 2-上班时间 3-下班时间")
    @NotNull(message = "访问时间段不能为空")
    private Integer accessDateType;
}

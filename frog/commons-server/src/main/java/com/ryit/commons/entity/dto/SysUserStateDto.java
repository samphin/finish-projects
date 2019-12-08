package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户状态对象
 */
@Data
@ApiModel(value = "用户状态对象", description = "用作修改用户状态")
public class SysUserStateDto implements Serializable {

    @ApiModelProperty(name = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @ApiModelProperty(name = "用户状态：0-正常 1-冻结 2-注销", allowableValues = "0,1,2")
    @NotNull(message = "用户状态不能为空")
    private Integer status;
}

package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "用户角色关联表数据传值")
@Data
public class SysUserRolePkDto implements Serializable {

    private static final long serialVersionUID = 3494879557026292241L;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @ApiModelProperty(value = "角色ID字符串集合", example = "1,2,3", required = true)
    @NotBlank(message = "角色ID不能为空，多个ID用逗号隔开")
    private String roleIds;
}
package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
@Data
public class SysRolePermissionPkDto implements Serializable {

    private static final long serialVersionUID = -5349939137628622353L;

    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID字符串集合", example = "1,2,3", required = true)
    @NotBlank(message = "权限ID拼接字符串，多个ID用逗号隔开")
    private String permissionIds;
}

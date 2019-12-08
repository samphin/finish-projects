package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "角色权限查询对象")
@Data
public class SysRolePermissionPkQueryDto {

    @ApiModelProperty(value = "角色ID", notes = "角色ID", required = true)
    private Integer roleId;

    @ApiModelProperty(value = "权限名称", notes = "权限名称")
    private String name;
}

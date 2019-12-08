package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 权限资源关联数据传值
 */
@ApiModel
@Data
public class SysPermissionResourcePkDto implements Serializable {

    private static final long serialVersionUID = -6667403859075205191L;

    @ApiModelProperty(value = "权限ID")
    private Integer permissionId;

    @ApiModelProperty(value = "资源ID字符串集", example = "1,2,3")
    @NotBlank(message = "权限绑定的资源不能为空")
    private Integer resourceId;


}

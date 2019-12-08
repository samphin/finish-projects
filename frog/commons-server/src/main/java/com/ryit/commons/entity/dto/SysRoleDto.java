package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel
@Data
public class SysRoleDto extends BaseDto<Integer> implements Serializable {

    private static final long serialVersionUID = -8465060390251556627L;

    @ApiModelProperty(value = "角色名称", notes = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 默认激活状态 true或false
     */
    @ApiModelProperty(value = "菜单状态", allowableValues = "true,false")
    private Boolean status;

    @ApiModelProperty(value = "角色描述", notes = "角色描述")
    private String description;
}
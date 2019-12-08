package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel
@Data
public class SysPermissionDto extends BaseDto<Integer> implements Serializable {

    @ApiModelProperty(value = "权限名称", required = true, dataType = "String")
    @NotBlank(message = "权限名称不能为空", groups = {Save.class, Update.class})
    private String name;

    @ApiModelProperty(value = "权限编码", required = true, dataType = "String")
    @NotBlank(message = "权限编码不能为空", groups = {Save.class, Update.class})
    private String code;

    /**
     * 默认权限是启用状态
     */
    @ApiModelProperty(value = "权限状态", allowableValues = "true,false")
    @NotNull(message = "状态不能为空", groups = Status.class)
    private Boolean status;

    @ApiModelProperty(value = "权限描述")
    private String description;

    /**
     * 父节点
     */
    private List<Integer> parentResourceIds;

    /**
     * 权限绑定的资源信息
     */
    private List<Integer> resourceIds;

    /**
     * 定义状态修改校验组
     */
    public interface Status {


    }
}
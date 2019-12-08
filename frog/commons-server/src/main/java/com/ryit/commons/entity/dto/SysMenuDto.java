package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 菜单Dto
 *
 * @author samphin
 * @since 2019-10-16 09:59:20
 */
@ApiModel
@Data
public class SysMenuDto extends BaseDto<Integer> implements Serializable {

    private static final long serialVersionUID = -1926570277414034819L;

    /**
     * 默认为一级菜单，父级菜单ID为-1
     */
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空",groups = {Save.class,Update.class})
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单序列号")
    private Integer sort;

    /**
     * 默认菜单为一级菜单
     */
    @ApiModelProperty(value = "菜单层级")
    private Integer level;

    /**
     * 默认没有子菜单
     */
    @ApiModelProperty(value = "菜单是否为叶子菜单")
    private Boolean leaf;

    @ApiModelProperty(value = "菜单类型",allowableValues = "Menu,Button")
    @NotBlank(message = "菜单类型不能为空",groups = {Save.class,Update.class})
    private String type;

    /**
     * 默认启用状态
     */
    @ApiModelProperty(value = "菜单状态", allowableValues = "true,false")
    private Boolean enabled;

    @ApiModelProperty(value = "描述")
    private String description;
}
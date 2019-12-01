package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品类别表
 *
 * @author samphin
 * @since 2019-10-21 17:42:47
 */
@Data
@ApiModel(value = "商品分类")
public class BusiGoodsBrandDto extends BaseDto<Long> implements Serializable {

    /**
     * 类别名称
     */
    @ApiModelProperty(name = "商品类别名称")
    @NotBlank(message = "类别名称不能为空", groups = {Save.class, Update.class})
    private String brandName;

    /**
     * 图片路径
     */
    @ApiModelProperty(name = "图片路径")
    @NotBlank(message = "类别图片不能为空", groups = {Save.class, Update.class})
    private String imgPath;

    /**
     * 上级id
     */
    @ApiModelProperty(name = "上级类别ID")
    private Long parentId;

    /**
     * 分类级别 1:一级分类 2:二级分类 3:三级分类
     */
    @ApiModelProperty(name = "分类级别 1:一级分类 2:二级分类 3:三级分类")
    private Integer levels;

    /**
     * 排序
     */
    private Integer sort;
}
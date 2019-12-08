package com.ryit.commons.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Dto基类
 *
 * @author samphin
 * @since 2019-10-22 16:54:29
 */
@ApiModel(value = "分页查询对象")
@Data
public class BaseQueryDto<T> implements Serializable {

    private static final long serialVersionUID = -7362386200793813818L;

    /**
     * 泛型查询参数
     */
    private T param;

    @ApiModelProperty(value = "页码", notes = "页码", dataType = "Integer")
    private int pageNum = 1;

    @ApiModelProperty(value = "页大小", notes = "页大小", dataType = "Integer")
    private int pageSize = 10;
}

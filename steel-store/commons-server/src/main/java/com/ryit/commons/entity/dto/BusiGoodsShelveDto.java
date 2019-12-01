package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 上/下架Dto类
 *
 * @author samphin
 * @since 2019-11-15 14:52:00
 */
@Data
@ApiModel(value = "商品上加｜下架参数对象")
public class BusiGoodsShelveDto implements Serializable {

    private static final long serialVersionUID = 1080119887288206294L;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "多个商品ID")
    private List<Long> ids;

    /**
     * 上架商品 0未发布 1上架 2下架
     */
    @ApiModelProperty(value = "商品上架状态：0-未发布 1-上架 2-下架", allowableValues = "0,1,2")
    private Integer shelfStatus;
}
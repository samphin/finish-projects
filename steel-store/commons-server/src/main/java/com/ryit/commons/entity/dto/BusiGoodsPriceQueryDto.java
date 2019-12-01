package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当日单价
 *
 * @author samphin
 * @since 2019-10-25 12:42:34
 */
@Data
public class BusiGoodsPriceQueryDto implements Serializable {

    /**
     * 分类ID
     */
    private Long brandId;

    /**
     * 商名称
     */
    private String goodsName;

    /**
     * 当日单价最小值
     */
    private Integer priceMin;

    /**
     * 当日单价最大值
     */
    private Integer priceMax;

    /**
     * 形状
     */
    private Integer shapeCode;
    /**
     * 默认厚度
     */
    private Integer defaultThickness;
    /**
     * 默认宽度
     */
    private Double defaultWidth;

    /**
     * 创建人名称
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;
}
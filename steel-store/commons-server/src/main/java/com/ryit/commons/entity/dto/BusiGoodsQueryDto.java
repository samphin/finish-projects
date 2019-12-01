package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品查询对象
 *
 * @author samphin
 * @since 2019-10-21 17:41:50
 */
@Data
public class BusiGoodsQueryDto implements Serializable {

    private static final long serialVersionUID = 4491862734188443590L;

    /**
     * 分类ID
     */
    private Long brandId;

    /**
     * 商品形状类型
     */
    private Integer shapeCode;

    /**
     * 厚度最小值
     */
    private Integer thicknessMin;

    /**
     * 厚度最大值
     */
    private Integer thicknessMax;

}
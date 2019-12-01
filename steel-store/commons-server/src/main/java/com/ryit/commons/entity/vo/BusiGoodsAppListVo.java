package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoods;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品列表Vo类
 *
 * @author samphin
 * @since 2019-11-15 12:01:45
 */
@Data
public class BusiGoodsAppListVo extends BaseVo<Long,BusiGoods, BusiGoodsAppListVo> implements Serializable {

    private static final long serialVersionUID = 3604741771555278555L;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品列表图片
     */
    private String imgPath;

    /**
     * 市场价
     */
    private Double marketPrice;

    /**
     * 促销价
     */
    private Double discountPrice;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long brandId;

    /**
     * 商品形状名称
     */
    private String shapeName;

    /**
     * 默认厚度
     */
    private Integer defaultThickness;
}
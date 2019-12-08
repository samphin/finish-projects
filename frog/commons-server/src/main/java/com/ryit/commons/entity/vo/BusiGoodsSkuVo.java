package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsSku;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;

/**
 * 商品规格表
 *
 * @author samphin
 * @since 2019-10-21 17:44:09
 */
@Data
public class BusiGoodsSkuVo extends BaseVo<Long, BusiGoodsSku, BusiGoodsSkuVo> implements Serializable {

    private static final long serialVersionUID = -6691563644408612107L;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 长度
     */
    private Double length;

    /**
     * 宽度
     */
    private Double width;

    /**
     * 厚度
     */
    private Double thickness;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 颜色
     */
    private String colour;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 组织id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orgId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 产生金额
     */
    private Double price;
}
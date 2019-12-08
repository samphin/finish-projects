package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品规格表
 *
 * @author samphin
 * @since 2019-10-21 17:44:09
 */
@Data
public class BusiGoodsSkuDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = -9074935813190045349L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品列表图标
     */
    private String goodsImg;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 废铁重量（针对异型板而言）
     */
    private Double scrapIronWeight;

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
     * 规格单价金额 1份的价格
     */
    private Double price;

    /**
     * 规格产生总价
     */
    private Double totalPrice;
}
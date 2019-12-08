package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品规格购买信息  vo类
 *
 * @author samphin
 * @since 2019-10-29 13:47:06
 */
@Data
public class BusiGoodsSkuInfoVo implements Serializable {

    private static final long serialVersionUID = 772909103328304793L;

    /**
     * 商品规格ID集合
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商品规格购买数量
     */
    private Integer amount;

    /**
     * 商品规格价格
     */
    private Double price;

    /**
     * 规格产生总价
     */
    private Double totalPrice;

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsImg;
}
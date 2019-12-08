package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsPrice;
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
public class BusiGoodsPriceVo extends BaseVo<Long, BusiGoodsPrice, BusiGoodsPriceVo> implements Serializable {

    private static final long serialVersionUID = 5976579425928241178L;

    /**
     * 分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long brandId;

    /**
     * 商品ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 规格ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

    /**
     * 当日单价
     */
    private Integer price;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改人ID
     */
    private Integer lastUpdateUserId;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;
}
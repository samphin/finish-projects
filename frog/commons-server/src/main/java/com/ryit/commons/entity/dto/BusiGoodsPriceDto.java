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
public class BusiGoodsPriceDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 7793883333590819446L;

    /**
     * 分类ID
     */
    private Long brandId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 规格ID
     */
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
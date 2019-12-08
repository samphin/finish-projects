package com.ryit.commons.entity.dto;

import com.ryit.commons.base.po.BasePo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 废铁价格类
 *
 * @author samphin
 * @since 2019-11-26 15:38:14
 */
@Data
public class BusiGoodsRolledPriceDto implements Serializable {

    /**
     * 当日废铁单价
     */
    private Integer price;
}
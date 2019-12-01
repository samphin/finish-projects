package com.ryit.commons.entity.vo;

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
public class BusiGoodsRolledPriceListVo implements Serializable {

    /**
     * 当日废铁单价
     */
    private Integer price;

    /**
     * 创建时间
     */
    private Date createDate;
}
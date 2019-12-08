package com.ryit.commons.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BusiGoodsRolledPriceQueryDto implements Serializable {

    private static final long serialVersionUID = 3651808272210269613L;

    /**
     * 当日废铁单价最小值
     */
    private Integer priceMin;

    /**
     * 当日废铁单价最大值
     */
    private Integer priceMax;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date createStartDate;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date createEndDate;
}
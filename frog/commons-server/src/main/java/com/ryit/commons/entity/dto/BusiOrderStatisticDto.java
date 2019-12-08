package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单营业额统计Dto
 *
 * @author samphin
 * @since 2019-10-21 17:45:53
 */
@Data
@ApiModel(value = "订单营业额参数对象")
public class BusiOrderStatisticDto implements Serializable {


    private static final long serialVersionUID = 8066744152029239578L;

    /**
     * 年月日标识
     */
    @ApiModelProperty(value = "年月日标识", allowableValues = "year,month,day")
    private String code;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date endDate;
}
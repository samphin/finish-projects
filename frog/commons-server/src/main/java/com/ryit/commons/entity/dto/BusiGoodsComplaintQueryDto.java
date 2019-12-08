package com.ryit.commons.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "商品投诉列表查询对象")
public class BusiGoodsComplaintQueryDto implements Serializable {

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 投诉人名称
     */
    @ApiModelProperty(value = "投诉人名称")
    private String complaintUserName;

    /**
     * 投诉状态0-处理中 1-已处理
     */
    private Integer status;

    /**
     * 投诉开始时间
     */
    @ApiModelProperty(value = "投诉开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date complaintStartDate;

    /**
     * 投诉结束时间
     */
    @ApiModelProperty(value = "投诉结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date complaintEndDate;


}
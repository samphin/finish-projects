package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价查询对象
 *
 * @author samphin
 * @since 2019-10-21 17:43:15
 */
@Data
@ApiModel(value = "商品评价查询对象")
public class BusiGoodsCommentsQueryDto implements Serializable {

    private static final long serialVersionUID = 228475733173846674L;

    /**
     * 商品名称
     */
    @ApiModelProperty(name = "商品名称")
    private String goodsName;

    /**
     * 评分级别 总共5颗星
     */
    @ApiModelProperty(name = "评分级别 总共5颗星")
    private Double grade;

    /**
     * 评价内容
     */
    @ApiModelProperty(name = "评价内容")
    private String content;

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "评价开始时间")
    private Date createStartDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "评价结束时间")
    private Date createEndDate;

    /**
     * 删除状态-->0：未删除 1：已删除
     */
    @ApiModelProperty(name = "删除状态-->0：未删除 1：已删除", allowableValues = "0,1")
    private Integer delStatus;
}
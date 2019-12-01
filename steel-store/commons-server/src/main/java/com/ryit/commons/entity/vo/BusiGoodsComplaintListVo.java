package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.bson.BusiGoodsComplaint;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BusiGoodsComplaintListVo extends BaseVo<Long, BusiGoodsComplaint, BusiGoodsComplaintListVo> implements Serializable {

    private static final long serialVersionUID = 1897676346847106634L;

    /**
     * 订单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品列表图标
     */
    private String goodsImgPath;

    /**
     * 规格ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

    /**
     * 长度(单位：米，精确到小数点后3位，四舍五入)
     */
    private Double length;

    /**
     * 宽度(单位：米，精确到小数点后3位，四舍五入)
     */
    private Double width;

    /**
     * 厚度(单位：毫米)
     */
    private Double thickness;

    /**
     * 重量（单位：吨，精确到小数点后3位，四舍五入）
     */
    private Double weight;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 产生金额
     */
    private Double totalPrice;

    /**
     * 投诉状态0-处理中 1-已处理
     */
    private Integer status;

    /**
     * 投诉人名称
     */
    private String complaintUserName;

    /**
     * 投诉内容
     */
    private String complaintContent;

    /**
     * 投诉时间
     */
    private Date complaintDate;

    /**
     * 处理人名称
     */
    private String handlerUserName;

    /**
     * 处理说明
     */
    private String handlerExplain;

    /**
     * 处理时间
     */
    private Date handlerDate;
}
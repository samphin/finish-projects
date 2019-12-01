package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.bson.BusiGoodsComments;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价列表对象
 *
 * @author samphin
 * @since 2019-10-21 17:43:15
 */
@Data
public class BusiGoodsCommentsListVo extends BaseVo<Long, BusiGoodsComments, BusiGoodsCommentsListVo> implements Serializable {

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
     * 商品列表图标
     */
    private String goodsImgPath;

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
     * 评价类型 1对动态的评价 2对评价的回复
     */
    private Integer type;

    /**
     * 删除状态 0正常 1删除
     */
    private Integer delStatus;

    /**
     * 评分级别 总共5颗星
     */
    private Double grade;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价/回复用户名称
     */
    private String createUserName;

    /**
     * 评价/回复时间
     */
    private Date createDate;


}
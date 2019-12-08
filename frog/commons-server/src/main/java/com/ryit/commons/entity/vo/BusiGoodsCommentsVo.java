package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.bson.BusiGoodsComments;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价表
 *
 * @author samphin
 * @since 2019-10-21 17:43:15
 */
@Data
public class BusiGoodsCommentsVo extends BaseVo<Long, BusiGoodsComments, BusiGoodsCommentsVo> implements Serializable {

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 规格id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

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
     * 主评价id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 回复评价id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long replyId;

    /**
     * 服务评价
     */
    private Double serviceGrade;

    /**
     * 物流评价
     */
    private Double logisticsGrade;

    /**
     * 是否追评 0否 1是
     */
    private Integer reviewStatus;

    /**
     * 回复排序
     */
    private Integer sort;

    /**
     * 评价/回复时间
     */
    private Date createDate;

    /**
     * 评价/回复人id
     */
    private Integer createUserId;

    /**
     * 评价/回复用户名称
     */
    private String createUserName;

    /**
     * 商口回复图片（暂无）
     */
    //private List<BusiGoodsCommentsImgs> images;
}
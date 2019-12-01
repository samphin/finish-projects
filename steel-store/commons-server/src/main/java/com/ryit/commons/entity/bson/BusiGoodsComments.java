package com.ryit.commons.entity.bson;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiGoodsCommentsDto;
import lombok.Data;
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
@Document(collection = "Busi_Goods_Comments")
public class BusiGoodsComments extends BasePo<Long, BusiGoodsCommentsDto, BusiGoodsComments> implements Serializable {

    private static final long serialVersionUID = -5483470356212844304L;

    /**
     * 商品id
     */
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
     * 规格id
     */
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
     * 上级评价id
     */
    private Long parentId;

    /**
     * 回复id
     */
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
}
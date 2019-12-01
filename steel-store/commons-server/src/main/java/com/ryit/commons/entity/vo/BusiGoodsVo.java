package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoods;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;
import java.util.Date;

/**
 * 商品表
 *
 * @author samphin
 * @since 2019-10-21 17:41:50
 */
@Data
public class BusiGoodsVo extends BaseVo<Long, BusiGoods, BusiGoodsVo> implements Serializable {

    private static final long serialVersionUID = 8120164515314057543L;

    /**
     * 组织id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orgId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String description;

    /**
     * 简介
     */
    private String content;

    /**
     * 成本价
     */
    private Double costPrice;

    /**
     * 市场价
     */
    private Double marketPrice;

    /**
     * 促销价
     */
    private Double discountPrice;

    /**
     * 库存数量
     */
    private Integer stockNums;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 默认厚度
     */
    private Integer defaultThickness;

    /**
     * 默认厚度
     */
    private Double defaultWidth;

    /**
     * 是否推荐 0:否 1:是
     */
    private Integer recommendStatus;

    /**
     * 是否新品 0:否 1:是
     */
    private Integer newStatus;

    /**
     * 是否热卖 0:否 1:是
     */
    private Integer hotStatus;

    /**
     * 邮费说明
     */
    private String postageDes;

    /**
     * 邮费
     */
    private Double postagePrice;

    /**
     * 是否包邮0否1是
     */
    private Integer freeShippingStatus;

    /**
     * 服务说明
     */
    private String serviceDes;

    /**
     * 参数说明
     */
    private String paramDes;

    /**
     * 分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long brandId;

    /**
     * 上架商品 0未发布 1上架 2下架
     */
    private Integer shelfStatus;

    /**
     * 商品列表图片
     */
    private String imgPath;

    /**
     * 访问数量
     */
    private Integer visit;

    /**
     * 点赞数
     */
    private Integer pointNum;

    /**
     * 收藏量
     */
    private Integer collectionNum;

    /**
     * 商品佣金
     */
    private Double commission;

    /**
     * 佣金返点设置（直接邀请人所占百分比，间接邀请人为剩余百分比）
     */
    private Double commissionPerct;

    /**
     * 商品评价数
     */
    private Integer commentCount;

    /**
     * 销售总数
     */
    private Integer sale;

    /**
     * 1:有效 0失效
     */
    private Integer status;

    /**
     * 商品拼音
     */
    private String nameSpell;

    /**
     * 商品拼音首字母(简拼)
     */
    private String nameSimpleSpell;

    /***************操作记录信息 Start*****************/
    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改人ID
     */
    private Integer lastUpdateUserId;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;

    /**
     * 板型编号
     */
    private Integer shapeCode;

}
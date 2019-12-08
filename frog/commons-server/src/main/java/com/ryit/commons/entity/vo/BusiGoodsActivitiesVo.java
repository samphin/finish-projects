package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsActivities;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品活动
 *
 * @author samphin
 * @since 2019-10-21 17:42:26
 */
@Data
public class BusiGoodsActivitiesVo extends BaseVo<Long, BusiGoodsActivities, BusiGoodsActivitiesVo> implements Serializable {

    private static final long serialVersionUID = -4720392580754093765L;
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
     * 组织id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orgId;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动价格
     */
    private Double price;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 是否展示0:展示；1:不展示
     */
    private Integer visible;

    /**
     * 活动banner图片链接
     */
    private String imageUrl;

    /**
     * 活动页面地址
     */
    private String pageUrl;
}
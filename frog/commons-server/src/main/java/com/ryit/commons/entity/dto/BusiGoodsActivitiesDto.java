package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import com.ryit.commons.base.po.BasePo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.lang.Double;
import java.util.Date;

/**
 * 商品活动表
 *
 * @author samphin
 * @since 2019-10-21 17:42:26
 */
@Data
@ApiModel(value = "商品活动")
public class BusiGoodsActivitiesDto  extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = -4720392580754093765L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 组织id
     */
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
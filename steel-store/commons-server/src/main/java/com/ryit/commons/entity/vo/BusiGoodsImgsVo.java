package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsImgs;
import lombok.Data;

import java.io.Serializable;

@Data
public class BusiGoodsImgsVo extends BaseVo<Long, BusiGoodsImgs, BusiGoodsImgsVo> implements Serializable {

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
     * 图片/或者视频路径
     */
    private String filePath;

    /**
     * 文件类型 0 图片 1 视频
     */
    private Short type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否是图文详情 0否 1是
     */
    private Short detailsStatus;
}
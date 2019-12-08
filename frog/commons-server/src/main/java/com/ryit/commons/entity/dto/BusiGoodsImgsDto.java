package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class BusiGoodsImgsDto extends BaseDto<Long> implements Serializable {

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 规格id
     */
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
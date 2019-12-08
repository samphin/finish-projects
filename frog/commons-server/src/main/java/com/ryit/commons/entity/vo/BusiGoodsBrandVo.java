package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsBrand;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品类别表
 *
 * @author samphin
 * @since 2019-10-21 17:42:47
 */
@Data
public class BusiGoodsBrandVo extends BaseVo<Long, BusiGoodsBrand, BusiGoodsBrandVo> implements Serializable {

    private static final long serialVersionUID = 6770123492218605668L;

    /**
     * 类别名称
     */
    private String brandName;

    /**
     * 图片路径
     */
    private String imgPath;

    /**
     * 上级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 分类级别 1:一级分类 2:二级分类 3:三级分类
     */

    private Integer levels;

    /**
     * 排序
     */
    private Integer sort;
}
package com.ryit.commons.entity.vo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsShape;
import lombok.Data;

import java.io.Serializable;

/**
 * 选择商品形状Vo类
 *
 * @author samphin
 * @since 2019-11-15 11:34:26
 */
@Data
public class BusiGoodsShapeListVo extends BaseVo<Long,BusiGoodsShape,BusiGoodsShapeListVo> implements Serializable {

    private static final long serialVersionUID = -1836477663938820415L;

    /**
     * 形状名称
     */
    private String name;

    /**
     * 形状类型编号
     */
    private Integer code;

}
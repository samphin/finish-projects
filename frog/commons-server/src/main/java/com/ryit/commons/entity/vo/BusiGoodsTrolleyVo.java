package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import com.ryit.commons.base.dto.BaseDto;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiGoodsTrolley;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
public class BusiGoodsTrolleyVo extends BaseVo<Long, BusiGoodsTrolley,BusiGoodsTrolleyVo> implements Serializable {

    private static final long serialVersionUID = 1186594052460413941L;

    /**
     * 商品ID
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
    private String goodsImg;

    /**
     * 商品规格长度
     */
    private Double length;

    /**
     * 商品规格宽度
     */
    private Double width;

    /**
     * 商品规格重量
     */
    private Double weight;

    /**
     * 厚度
     */
    private Double thickness;

    /**
     * 商品规格数量
     */
    private Integer amount;

    /**
     * 商品规格单价
     */
    private Double price;

    /**
     * 商品规格总价
     */
    private Double totalPrice;

    /**
     * 逻辑删除标识0：未删除 1：已删除
     */
    private Integer delStatus;
}
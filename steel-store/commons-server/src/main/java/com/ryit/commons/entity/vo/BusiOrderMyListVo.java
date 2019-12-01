package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 订单列表Vo类
 *
 * @author samphin
 * @since 2019-10-29 17:05:52
 */
@Data
public class BusiOrderMyListVo implements Serializable {

    private static final long serialVersionUID = 537971031766985013L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单状态(0-待付款，1-待发货，2-待收货，3-已取消，4-已完成)
     */
    private Integer status;

    /**
     * 订单类型(0 普通,1 限时抢购,秒杀, 2 团购 , 3 促销优惠)
     */
    private Integer type;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GTM+8")
    private Date createDate;

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
     * 商品规格数量
     */
    private Integer amount;

    /**
     * 商品规格合计价格
     */
    private Double price;

    /**
     * 发货状态（0-未发货、1-已发货、2-部分发货）
     */
    private Integer logisticsStatus;

    public List<BusiOrderMyListVo> buildVoList(List<BusiOrderListVo> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return new ArrayList<>();
        }
        return Lists.transform(orders, source -> {
            BusiOrderMyListVo target = new BusiOrderMyListVo();
            BeanUtils.copyProperties(source, target);
            return target;
        });
    }
}

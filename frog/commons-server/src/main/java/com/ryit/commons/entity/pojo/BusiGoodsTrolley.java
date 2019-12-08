package com.ryit.commons.entity.pojo;

import com.google.common.collect.Lists;
import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyDto;
import com.ryit.commons.util.SnowflakeIdWorker;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 *
 * @author samphin
 * @since 2019-10-22 10:11:41
 */
@Data
public class BusiGoodsTrolley extends BasePo<Long, BusiGoodsTrolleyDto, BusiGoodsTrolley> implements Serializable {

    private static final long serialVersionUID = 1186594052460413941L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品列表图片
     */
    private String goodsImg;

    /**
     * 规格长度
     */
    private Double length;

    /**
     * 规格宽度
     */
    private Double width;

    /**
     * 规格厚度
     */
    private Double thickness;

    /**
     * 规格重量
     */
    private Double weight;

    /**
     * 规格数量
     */
    private Integer amount;

    /**
     * 规格单价
     */
    private Double price;

    /**
     * 规格合计总价
     */
    private Double totalPrice;

    /**
     * 逻辑删除标识0：未删除 1：已删除
     */
    private Integer delStatus;

    /**
     * 创建人
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
     * 将规格集合转换成购物车信息
     */
    public List<BusiGoodsTrolley> buildPoList(List<BusiGoodsTrolleyDto> trolleyDtos, Integer userId) {
        return Lists.transform(trolleyDtos, trolleyDto -> {
            BusiGoodsTrolley po = new BusiGoodsTrolley();
            BeanUtils.copyProperties(trolleyDto, po);
            po.setId(SnowflakeIdWorker.generateId());
            //单价设置【公式：总价/数量】
            //总价
            BigDecimal totalPrice = BigDecimal.valueOf(trolleyDto.getTotalPrice());
            //数量
            BigDecimal amount = BigDecimal.valueOf(trolleyDto.getAmount());
            //单价
            BigDecimal price = totalPrice.divide(amount, BigDecimal.ROUND_CEILING);
            po.setPrice(price.doubleValue());
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());
            return po;
        });
    }
}
package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiGoodsSkuDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品规格表
 *
 * @author samphin
 * @since 2019-10-21 17:44:09
 */
@Data
public class BusiGoodsSku extends BasePo<Long, BusiGoodsSkuDto, BusiGoodsSku> implements Serializable {

    private static final long serialVersionUID = -9074935813190045349L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 长度(单位：米，精确到小数点后3位，四舍五入)
     */
    private Double length;

    /**
     * 宽度(单位：米，精确到小数点后3位，四舍五入)
     */
    private Double width;

    /**
     * 厚度(单位：毫米)
     */
    private Double thickness;

    /**
     * 重量（单位：吨，精确到小数点后3位，四舍五入）
     */
    private Double weight;

    /**
     * 废铁重量（针对异型板而言）
     */
    private Double scrapIronWeight;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 颜色
     */
    private String colour;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 规格单价金额 1份的价格
     */
    private Double price;

    /**
     * 规格产生总价
     */
    private Double totalPrice;


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
     * 计算规格产生金额
     * <p>
     * 计算公式：重量*数量*今日单价（由管理员手动设置）
     * 重量 = 长 × 宽 × 厚 × 7.85
     * 价格 = 重量 × 单价
     * 注意：长宽厚单位均为 米
     * @param sku
     * @return
     */
    public Double calculatePrice(BusiGoodsSku sku, Integer newestPrice) {

        /**
         * 重量
         */
        Double weight = sku.getWeight();

        /**
         * 数量
         */
        Integer amount = sku.getAmount();

        return BigDecimal.valueOf(weight).multiply(BigDecimal.valueOf(amount)).
                multiply(BigDecimal.valueOf(newestPrice)).
                setScale(BigDecimal.ROUND_CEILING).doubleValue();
    }
}
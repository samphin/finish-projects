package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiOrderReturnDto;
import com.ryit.commons.entity.dto.BusiSubmitOrdersDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 退货申请对象
 *
 * @author samphin
 * @since 2019-11-25 13:53:52
 */
@Data
public class BusiOrderReturn extends BasePo<Long, BusiOrderReturnDto, BusiOrderReturn> implements Serializable {

    private static final long serialVersionUID = 30511631740537448L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品规格id
     */
    private Long skuId;

    /**
     * 退款金额
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private Integer nums;

    /**
     * 0退货1换货
     */
    private Short type;

    /**
     * 退换货原因
     */
    private String reason;

    /**
     * 说明
     */
    private String remark;

    /**
     * 拍照图片路径
     */
    private String imgs;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 0申请中1处理中2已完成
     */
    private Short status;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;


    public BusiOrderReturn buildPo(BusiOrderReturnDto dto) {
        BeanUtils.copyProperties(dto, this,new String[]{"imgs"});
        return this;
    }
}
package com.ryit.commons.entity.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 微信申请退款Dto
 *
 * @author samphin
 * @date 2019-9-20 15:04:44
 */
@Data
public class WeChatRefundDto implements Serializable {

    private static final long serialVersionUID = -219520350876647914L;

    /**
     * 微信订单号
     */
    private String transaction_id;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 商户退款单号
     */
    private String out_refund_no;

    /**
     * 订单金额
     */
    private Integer total_fee;

    /**
     * 退款金额
     */
    private Integer refund_fee;

}

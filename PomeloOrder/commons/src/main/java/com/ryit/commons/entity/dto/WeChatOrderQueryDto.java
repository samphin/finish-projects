package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信订单查询Dto
 *
 * @author samphin
 * @date 2019-9-20 15:04:44
 */
@Data
public class WeChatOrderQueryDto implements Serializable {

    private static final long serialVersionUID = -219520350876647914L;

    /**
     * 充值方案编号（柚子抢单系统字段）
     */
    private Long rechargeId;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 交易状态码
     */
    private String errCode;

}

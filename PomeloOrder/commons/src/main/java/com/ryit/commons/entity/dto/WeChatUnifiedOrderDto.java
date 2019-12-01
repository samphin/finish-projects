package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 微信统一下单Dto
 *
 * @author samphin
 * @date 2019-9-20 15:04:58
 */
@Data
public class WeChatUnifiedOrderDto implements Serializable {

    private static final long serialVersionUID = 3038395696995388097L;

    /**
     * 充值金额
     */
    private Double money;

    /**
     * 赠送金额
     */
    private Double gift;

}

package com.ryit.commons.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单拼团详情
 *
 * @author samphin
 * @since 2019-10-21 17:45:53
 */
@Data
public class BusiOrderGroupDetailDto implements Serializable {

    private static final long serialVersionUID = 3135060299811427724L;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 我的下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date createDate;
}
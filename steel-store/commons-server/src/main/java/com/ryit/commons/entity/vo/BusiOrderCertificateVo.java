package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiOrderCertificate;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付凭证Vo类
 *
 * @author samphin
 * @since 2019-11-4 15:54:49
 */
@Data
public class BusiOrderCertificateVo extends BaseVo<Long, BusiOrderCertificate, BusiOrderCertificateVo> implements Serializable {

    private static final long serialVersionUID = 4432543802561167971L;

    /**
     * 订单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 凭证图片ID
     */
    private String imageId;
}
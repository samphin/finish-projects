package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiOrderCertificateDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BusiOrderCertificate extends BasePo<Long, BusiOrderCertificateDto, BusiOrderCertificate> implements Serializable {

    private static final long serialVersionUID = 7817673584047917737L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 凭证图片ID
     */
    private String imageId;

    /**
     * 上传时间
     */
    private Date createDate;
}
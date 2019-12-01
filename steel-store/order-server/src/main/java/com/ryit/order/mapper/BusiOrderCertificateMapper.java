package com.ryit.order.mapper;

import com.ryit.commons.entity.pojo.BusiOrderCertificate;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiOrderCertificateMapper extends Mapper<BusiOrderCertificate> {

    /**
     * 批量上传支付凭证
     *
     * @param poList
     * @return
     */
    Integer insertBatch(List<BusiOrderCertificate> poList);
}
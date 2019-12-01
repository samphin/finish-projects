package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiCompany;
import lombok.Data;

import java.io.Serializable;

/**
 * 公司信息
 *
 * @author samphin
 * @since 2019-10-22 12:22:14
 */
@Data
public class BusiCompanyVo extends BaseVo<Long, BusiCompany, BusiCompanyVo> implements Serializable {

    private static final long serialVersionUID = 681248708600256433L;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司性质
     */
    private String companyType;

    /**
     * 纳税人识别号
     */
    private String taxpayerCode;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 公司电话
     */
    private String telephone;

    /**
     * 账号
     */
    private String account;

    /**
     * 开户行
     */
    private String belongBank;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactsPhone;
}
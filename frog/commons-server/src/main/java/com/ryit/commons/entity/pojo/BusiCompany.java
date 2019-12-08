package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiCompanyDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司信息
 *
 * @author samphin
 * @since 2019-10-22 12:22:14
 */
@Data
public class BusiCompany extends BasePo<Long, BusiCompanyDto, BusiCompany> implements Serializable {

    private static final long serialVersionUID = -5456378603166714892L;

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
}
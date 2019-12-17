package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.SysUserCallInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户回访记录表
 */
@Data
public class SysUserCallInfoListVo extends BaseVo<Long, SysUserCallInfo, SysUserCallInfoListVo> implements Serializable {

    private static final long serialVersionUID = -5269683772933187721L;

    /**
     * 咨询人手机号
     */
    private String telephone;

    /**
     * 咨询人姓名
     */
    private String realName;

    /**
     * 最近回访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GTM+8")
    private Date createDate;
}
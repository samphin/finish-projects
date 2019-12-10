package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.SysUserCallInfoDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户回访记录表
 */
@Data
public class SysUserCallInfo extends BasePo<Long, SysUserCallInfoDto, SysUserCallInfo> implements Serializable {

    /**
     * 咨询人ID
     */
    private Long userId;

    /**
     * 咨询人手机号
     */
    private String telephone;

    /**
     * 咨询人姓名
     */
    private String realName;

    /**
     * 回访时间
     */
    private Date createDate;
}
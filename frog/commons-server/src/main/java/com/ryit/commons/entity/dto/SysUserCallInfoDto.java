package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户回访记录表
 */
@Data
public class SysUserCallInfoDto implements Serializable {

    private static final long serialVersionUID = -6669850082449999621L;

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
}
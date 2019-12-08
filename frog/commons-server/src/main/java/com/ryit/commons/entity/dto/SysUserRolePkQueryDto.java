package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色查询对象
 */
@Data
public class SysUserRolePkQueryDto implements Serializable {

    private static final long serialVersionUID = 2079537189849393827L;

    private Integer userId;

    private String name;
}
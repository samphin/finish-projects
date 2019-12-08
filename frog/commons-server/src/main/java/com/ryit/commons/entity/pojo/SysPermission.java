package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.SysPermissionDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysPermission extends BasePo<Integer, SysPermissionDto, SysPermission> implements Serializable {

    private static final long serialVersionUID = -4997497350114453346L;

    private String name;

    private String code;

    private String description;

    private Boolean status;

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
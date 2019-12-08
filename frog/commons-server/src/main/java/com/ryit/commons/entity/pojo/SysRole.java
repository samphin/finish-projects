package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.SysRoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class SysRole extends BasePo<Integer, SysRoleDto, SysRole> implements Serializable {

    private static final long serialVersionUID = -8652370741763876525L;

    private String name;

    private String code;

    private Boolean status;

    private String description;

    public SysRole(String name, Boolean status) {
        this.name = name;
        this.status = status;
    }

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
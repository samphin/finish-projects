package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.SysMenuDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMenu extends BasePo<Integer, SysMenuDto, SysMenu> implements Serializable {

    private static final long serialVersionUID = 1174481733096961365L;

    private Integer parentId;

    private String name;

    private String url;

    private String icon;

    private Integer sort;

    private Integer level;

    private Boolean leaf;

    private String type;

    private Boolean enabled;

    private String description;

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
package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysMenuVo extends BaseVo<Integer, SysMenu, SysMenuVo> implements Serializable {

    private static final long serialVersionUID = 5343672031825168218L;

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

    /**
     * 子菜单集合
     */
    List<SysMenuVo> children = new ArrayList<>();
}
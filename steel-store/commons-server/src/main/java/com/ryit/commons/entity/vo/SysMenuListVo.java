package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.SysMenu;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单列表Vo类
 *
 * @author samphin
 * @since 2019-11-7 15:03:28
 */
@Data
public class SysMenuListVo extends BaseVo<Integer, SysMenu, SysMenuListVo> implements Serializable {

    private static final long serialVersionUID = 2834422691299826030L;

    private Integer parentId;

    private String name;

    private String url;

    private String icon;

    private Integer sort;

    private Integer level;

    private Boolean enabled;

    private String description;
}
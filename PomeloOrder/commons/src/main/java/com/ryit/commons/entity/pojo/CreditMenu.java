package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 武汉软艺
 **/
@Getter
@Setter
@NoArgsConstructor
public class CreditMenu implements Serializable {

    private static final long serialVersionUID = 2270904394574069586L;

    /**
     *  菜单ID
     */
    private Long menuId;

    /**
     *  菜单名称
     */
    private String menuName;

    /**
     *  父菜单ID
     */
    private Long parentId;

    /**
     *  显示顺序
     */
    private Long orderNum;

    /**
     *  请求地址
     */
    private String url;

    /**
     *  菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     *  菜单状态（0显示 1隐藏）
     */
    private String visible;

    /**
     *  权限标识
     */
    private String perms;

    /**
     *  菜单图标
     */
    private String icon;

    /** 子菜单 */
    private List<CreditMenu> children = new ArrayList<CreditMenu>();

}

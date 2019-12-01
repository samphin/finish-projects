package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/9/18 0018上午 10:54
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletMenu implements Serializable {

    private static final long serialVersionUID = 1568775243415L;


    /**
     * 主键
     * 菜单ID
     * isNullAble:0
     */
    private Long menuId;

    /**
     * 菜单名称
     * isNullAble:0
     */
    private String menuName;

    /**
     * 父菜单ID
     * isNullAble:1,defaultVal:0
     */
    private Long parentId;

    /**
     * 显示顺序
     * isNullAble:1,defaultVal:0
     */
    private Integer orderNum;

    /**
     * 请求地址
     * isNullAble:1,defaultVal:#
     */
    private String url;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     * isNullAble:1,defaultVal:
     */
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     * isNullAble:1,defaultVal:0
     */
    private String visible;

    /**
     * 权限标识
     * isNullAble:1,defaultVal:
     */
    private String perms;

    /**
     * 菜单图标
     * isNullAble:1,defaultVal:#
     */
    private String icon;
}

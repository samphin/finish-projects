package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * @Author 武汉软艺
 *
 **/
@Getter
@Setter
@NoArgsConstructor
public class CreditRole implements Serializable{

    private static final long serialVersionUID = -8489633873245487978L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     *角色名称
     */
    private String roleName;

    /**
     * 角色字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限）
     */
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    private  String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;



}

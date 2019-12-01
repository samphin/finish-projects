package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/19 0019下午 2:19
 */
@Setter
@Getter
@NoArgsConstructor
public class WalletRoleDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -6280063749986258993L;

    /**
     * 主键
     * 角色ID
     * isNullAble:0
     */
    private Long roleId;

    /**
     * 角色名称
     * isNullAble:0
     */
    private String roleName;

    /**
     * 角色字符串
     * isNullAble:0
     */
    private String roleKey;

    /**
     * 显示顺序
     * isNullAble:0
     */
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限）
     * isNullAble:1,defaultVal:1
     */
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     * isNullAble:0
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     * isNullAble:1,defaultVal:0
     */
    private String delFlag;

    /**
     * 添加的菜单的id集合
     */
    private List<Long> menuIds;
}

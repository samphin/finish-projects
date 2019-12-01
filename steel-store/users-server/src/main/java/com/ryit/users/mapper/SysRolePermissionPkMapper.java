package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.SysRolePermissionPk;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRolePermissionPkMapper extends Mapper<SysRolePermissionPk> {

    /**
     * 给角色分配权限
     *
     * @param rolePermissionPkList
     * @return
     */
    int saveBatch(List<SysRolePermissionPk> rolePermissionPkList);

}
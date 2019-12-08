package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.SysPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysPermissionMapper extends Mapper<SysPermission> {

    /**
     * 查询角色拥有的权限信息
     *
     * @param roleId
     * @return
     */
    List<SysPermission> selectRoleHavePermissions(@Param("roleId") Integer roleId);

    /**
     * 查询角色未拥有的权限信息
     *
     * @param roleId
     * @param name
     * @return
     */
    List<SysPermission> selectRoleHaveNoPermissions(@Param("roleId") Integer roleId, @Param("name") String name);

    /**
     * 根据角色ID查询用户权限信息
     */
    List<String> selectPermissionsByRoleIds(List<Integer> roleIds);

    /**
     * 查询列表
     */
    List<SysPermission> selectList(SysPermission po);
}
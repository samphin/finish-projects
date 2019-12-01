package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.SysPermissionResourcePk;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysPermissionResourcePkMapper extends Mapper<SysPermissionResourcePk> {

    /**
     * 给权限分配资源
     *
     * @param rolePermissionPkList
     * @return
     */
    int insertBatch(List<SysPermissionResourcePk> rolePermissionPkList);

    /**
     * 查询权限已拥有的资源信息
     *
     * @param permissionId 权限ID
     * @return
     */
    List<SysPermissionResourcePk> selectPermissionHaveResource(@Param("permissionId") Integer permissionId);

}
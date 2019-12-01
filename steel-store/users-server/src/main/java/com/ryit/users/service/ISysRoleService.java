package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.SysRoleDto;
import com.ryit.commons.entity.dto.SysRolePermissionPkDto;
import com.ryit.commons.entity.dto.SysRolePermissionPkQueryDto;
import com.ryit.commons.entity.vo.SysPermissionVo;
import com.ryit.commons.entity.vo.SysRoleVo;

import java.util.List;

public interface ISysRoleService extends IBaseService<Integer, SysRoleDto, SysRoleVo> {

    /**
     * 保存用户绑定的角色信息
     *
     * @param dto
     * @return
     */
    boolean bindingPermissions(SysRolePermissionPkDto dto);

    /**
     * 查询角色拥有的权限
     *
     * @param roleId
     * @return
     */
    List<SysPermissionVo> queryRoleHavePermissions(Integer roleId);

    /**
     * 查询角色未拥有的权限
     *
     * @param dto
     * @return
     */
    List<SysPermissionVo> queryRoleHaveNoPermissions(SysRolePermissionPkQueryDto dto);

}

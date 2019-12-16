package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.SysPermissionDto;
import com.ryit.commons.entity.vo.SysPermissionVo;

import java.util.List;

public interface ISysPermissionService extends IBaseService<Integer, SysPermissionDto, SysPermissionVo> {

    /**
     * 用户快速启用、禁用权限
     *
     * @param dto
     * @return
     */
    boolean updateState(SysPermissionDto dto);


    /*************************权限分配资源***********************************/
    /**
     * 查询权限拥有的资源信息
     *
     * @param permissionId 权限ID
     * @return
     */
    List<Integer> queryPermissionHaveResource(Integer permissionId);
}

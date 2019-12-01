package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.SysRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {

    /**
     * 分页查询
     *
     * @param po
     * @return
     */
    List<SysRole> selectList(SysRole po);

    /**
     * 查询用户组已拥有的角色信息
     *
     * @param userGroupId
     * @return
     */
    List<SysRole> selectUserGroupHaveRoles(@Param("userGroupId") Integer userGroupId);


    /**
     * 查询用户组未拥有的角色信息
     *
     * @param userGroupId
     * @param name
     * @return
     */
    List<SysRole> selectUserGroupHaveNoRoles(@Param("userGroupId") Integer userGroupId, @Param("name") String name);

    /**
     * 查询用户已拥有的角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> selectUserHaveRoles(@Param("userId") Integer userId);

    /**
     * 查询用户未拥有的角色信息
     *
     * @param userId
     * @param name
     * @return
     */
    List<SysRole> selectUserHaveNoRoles(@Param("userId") Integer userId, @Param("name") String name);


    /**
     * 根据用户ID查询用户角色信息
     *
     * @return
     */
    List<SysRole> selectRolesByUserId(@Param("userId") Integer userId);
}
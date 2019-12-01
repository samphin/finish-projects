package com.ryit.walletuserserver.dao;

import com.ryit.commons.entity.dto.WalletRoleDto;
import com.ryit.commons.entity.pojo.WalletRole;
import com.ryit.commons.entity.vo.WalletRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/17 0017下午 7:34
 */
@Repository
public interface WalletRoleMapper {

    /**
     * 添加权限
     *
     * @param walletRole
     * @return
     */
    Integer insertWalletRole(WalletRole walletRole);

    /**
     * 修改权限
     *
     * @param walletRole
     * @return
     */
    Integer updateWalletRole(WalletRole walletRole);

    /**
     * 查询权限详情
     *
     * @param id
     * @return
     */
    WalletRoleVo getRoleInfo(Long id);

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    Integer deleteRole(Long id);

    /**
     * 条件查询权限列表
     *
     * @param walletRoleDto
     * @return
     */
    List<WalletRoleVo> queryRoleByCondition(WalletRoleDto walletRoleDto);

    /**
     * 添加权限关联的菜单
     *
     * @param roleId
     * @param list
     * @return
     */
    Integer insertRoleMenu(@Param("roleId") Long roleId, @Param("list") List<Long> list);

    /**
     * 删除权限关联的菜单
     *
     * @param roleId
     * @param list
     * @return
     */
    Integer deleteRoleMenu(@Param("roleId") Long roleId, @Param("list") List<Long> list);

    /**
     * 删除角色的所有关联菜单
     *
     * @param roleId
     * @return
     */
    Integer deleteAllRoleMenu(@Param("roleId") Long roleId);

    /**
     * 添加用户权限
     *
     * @param userId
     * @param roleId
     * @return
     */
    Integer insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除用户权限
     *
     * @param userId
     * @param list
     * @return
     */
    Integer deleteUserRole(@Param("userId") Long userId, @Param("list") List<Long> list);

    /**
     * 删除管理员关联的所有角色
     *
     * @param userId
     * @return
     */
    Integer deleteAllUserRole(@Param("userId") Long userId);

    /**
     * 用户id查询用户的权限列表
     *
     * @param userId
     * @return
     */
    List<WalletRoleVo> queryUserRole(Long userId);


    /**
     * 查询用户关联的角色id集合
     *
     * @param userId
     * @return
     */
    List<Long> queryRoleIdsByUser(Long userId);

    /**
     * 查询角色关联的菜单id集合
     *
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdsByRole(Long roleId);

    /**
     * 根据角色名或者角色编码查询角色是否存在(用于添加做唯一性校验)
     *
     * @param roleName
     * @param roleKey
     * @return
     */
    List<Long> queryByRoleNameOrRoleKey(@Param("roleName") String roleName, @Param("roleKey") String roleKey);
}

package com.ryit.walletuserserver.service;

import com.ryit.commons.entity.dto.WalletRoleDto;
import com.ryit.commons.entity.pojo.WalletMenu;
import com.ryit.commons.entity.pojo.WalletRole;
import com.ryit.commons.entity.vo.WalletMenuVo;
import com.ryit.commons.entity.vo.WalletRoleVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/21 0021下午 2:57
 */
public interface WalletRoleService {

    /**
     * 添加权限
     *
     * @param walletRole
     * @return
     */
    Boolean insertWalletRole(WalletRoleDto walletRole);

    /**
     * 修改权限信息
     *
     * @param walletRole
     * @return
     */
    Boolean updateWalletRole(WalletRoleDto walletRole);

    /**
     * id查询权限详情
     *
     * @param id
     * @return
     */
    WalletRoleVo getRoleInfo(Long id);

    /**
     * id删除权限
     *
     * @param id
     * @return
     */
    Boolean deleteRole(Long id);

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
     * @param list   菜单id集合
     * @return
     */
    Integer insertRoleMenu(Long roleId, List<Long> list);


    /**
     * 删除权限关联的菜单
     *
     * @param roleId
     * @param list   菜单id集合
     * @return
     */
    Integer deleteRoleMenu(Long roleId, List<Long> list);

    /**
     * 查询权限关联的菜单
     *
     * @param roleId
     * @return
     */
    List<WalletMenuVo> queryRoleMenu(Long roleId);

    /**
     * 添加用户权限
     *
     * @param userId
     * @param roleId 角色id
     * @return
     */
    Integer insertUserRole(Long userId, Long roleId);


    /**
     * 删除用户权限
     *
     * @param userId
     * @param list 角色id集合
     * @return
     */
    Integer deleteUserRole(Long userId, List<Long> list);

    /**
     * 用户id查询用户的权限列表
     *
     * @param userId
     * @return
     */
    List<WalletRoleVo> queryUserRole(Long userId);
}

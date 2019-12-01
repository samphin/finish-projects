package com.ryit.credituserserver.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Author 武汉软艺
 **/
@Repository("creditUserRoleMapper")
public interface CreditUserRoleMapper {

    /**
     * 保存用户角色
     * @param map
     * @return
     */
    Integer saveUserRole(Map map);

    /**
     *
     * @param userId
     * @return
     */
    Integer deleteUserRole(Long userId);

    /**
     *  查询角色分配数量
     * @param roleId
     * @return
     */
    Integer getRoleUserNum(Long roleId);


}

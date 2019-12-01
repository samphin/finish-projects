package com.ryit.credituserserver.dao;

import com.ryit.commons.entity.pojo.CreditMenu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author 武汉软艺
 **/
@Repository
public interface CreditRoleMenuMapper {

    /**
     * 保存角色权限
     * @param map
     * @return
     */
    Integer savaRoleMenu(Map map);

    /**
     *  删除角色权限
     * @param roleId
     * @return
     */
    Integer deleteRoleMenu(Long roleId);

    /**
     *  查询角色权限
     * @param roleId
     * @return
     */
    List<CreditMenu> getMenuByRoleId(Long roleId);


}

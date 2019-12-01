package com.ryit.credituserserver.service;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.CreditRoleDto;
import com.ryit.commons.entity.pojo.CreditMenu;
import com.ryit.commons.entity.pojo.CreditRole;
import com.ryit.commons.entity.vo.CreditRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author 武汉软艺
 **/
public interface CreditRoleService {

    /**
     * 根据条件分页查询角色数据
     * @param creditRoleDto
     * @return
     */
    List<CreditRole> getCreditRoleByPage(CreditRoleDto creditRoleDto);


    /**
     * 查看角色详情
     * @param roleId
     * @return
     */
    CreditRoleVo getCreditRoleInfo(Long roleId);

    /**
     * 添加角色
     * @param creditRoleDto
     * @return
     */
    Integer saveRole(CreditRoleDto creditRoleDto);

    /**
     * 编辑角色
     * @param creditRoleDto
     * @return
     */
    Integer editRole(CreditRoleDto creditRoleDto);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    AjaxResult deleteRole(Long roleId);


    /**
     * 检验角色名称是否唯一
     * @param roleName
     * @return
     */
    Integer checkRoleNameUnique(@Param("roleName") String roleName);

    /**
     *  校验权限字符是否唯一
     * @param roleKey
     * @return
     */
    Integer checkRoleKeyUnique(@Param("roleKey") String roleKey);

    /**
     * 保存用户角色
     * @param map
     * @return
     */
    Integer saveUserRole(Map map);

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<CreditMenu> getChildPerms(List<CreditMenu> list, int parentId);

    /**
     *  获取所有角色
     * @return
     */
    List<CreditRole> getCreditRoleAll();

    /**
     * 获取最底层菜单menuId
     * @param list
     * @return
     */
    List<Long> getMinChildIds(List<CreditMenu> list,int parentId);
}

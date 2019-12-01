package com.ryit.credituserserver.dao;

import com.ryit.commons.entity.dto.CreditRoleDto;
import com.ryit.commons.entity.pojo.CreditRole;
import com.ryit.commons.entity.vo.CreditRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author 武汉软艺
 **/
@Repository("creditRoleMapper")
public interface CreditRoleMapper {

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
    Integer deleteRole(Long roleId);


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
     *  获取所有角色
     * @return
     */
    List<CreditRole> getCreditRoleAll();



 }

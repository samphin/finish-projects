package com.ryit.credituserserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.CreditRoleDto;
import com.ryit.commons.entity.pojo.CreditMenu;
import com.ryit.commons.entity.pojo.CreditRole;
import com.ryit.commons.entity.vo.CreditRoleVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credituserserver.dao.CreditRoleMapper;
import com.ryit.credituserserver.dao.CreditRoleMenuMapper;
import com.ryit.credituserserver.dao.CreditUserRoleMapper;
import com.ryit.credituserserver.service.CreditRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author 武汉软艺
 **/
@Service("creditRoleService")
public class CreditRoleServiceImpl implements CreditRoleService {

    @Autowired
    private CreditRoleMapper creditRoleMapper;

    @Autowired
    private CreditRoleMenuMapper creditRoleMenuMapper;

    @Autowired
    private CreditUserRoleMapper creditUserRoleMapper;

    /**
     * 根据条件分页查询角色数据
     *
     * @param creditRoleDto
     * @return
     */
    @Override
    public List<CreditRole> getCreditRoleByPage(CreditRoleDto creditRoleDto) {
        try {
            return creditRoleMapper.getCreditRoleByPage(creditRoleDto);
        } catch (Exception e) {
            throw new CustomException("查询角色数据列表失败");
        }
    }

    /**
     * 查看角色详情
     *
     * @param roleId
     * @return
     */
    @Override
    public CreditRoleVo getCreditRoleInfo(Long roleId) {
        try {
            CreditRoleVo result = creditRoleMapper.getCreditRoleInfo(roleId);
            List<CreditMenu> menuList = creditRoleMenuMapper.getMenuByRoleId(roleId);
            result.setMenus(getMinChildIds(menuList, 0));
            return result;
        } catch (Exception e) {
            throw new CustomException("查看角色详情失败");
        }
    }

    /**
     * 添加角色
     *
     * @param creditRoleDto
     * @return
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRole(CreditRoleDto creditRoleDto) {
        try {
            Integer num = creditRoleMapper.saveRole(creditRoleDto);
            if (num > 0) {
                if (creditRoleDto.getMenus().length > 0) {
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("roleId", creditRoleDto.getRoleId());
                    map.put("menus", creditRoleDto.getMenus());
                    creditRoleMenuMapper.savaRoleMenu(map);
                }
            }
            return num;
        } catch (Exception e) {
            throw new CustomException("保存角色失败");
        }
    }

    /**
     * 编辑角色
     *
     * @param creditRoleDto
     * @return
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer editRole(CreditRoleDto creditRoleDto) {
        try {
            Integer num = creditRoleMapper.editRole(creditRoleDto);
            if (num > 0) {
                if (creditRoleDto.getMenus().length > 0) {
                    creditRoleMenuMapper.deleteRoleMenu(creditRoleDto.getRoleId());
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("roleId", creditRoleDto.getRoleId());
                    map.put("menus", creditRoleDto.getMenus());
                    creditRoleMenuMapper.savaRoleMenu(map);
                }
            }
            return num;
        } catch (Exception e) {
            throw new CustomException("编辑角色失败");
        }
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteRole(Long roleId) {
        //查询角色分配数量
        int count = creditUserRoleMapper.getRoleUserNum(roleId);
        if (count > 0) {
            return AjaxResult.error("改角色已分配，无法删除");
        }
        int num = creditRoleMapper.deleteRole(roleId);
        if (num > 0) {
            //删除关联的菜单
            creditRoleMenuMapper.deleteRoleMenu(roleId);
        }
        return AjaxResult.toAjax(num);
    }

    /**
     * 检验角色名称是否唯一
     *
     * @param roleName
     * @return
     */
    @Override
    public Integer checkRoleNameUnique(String roleName) {
        return creditRoleMapper.checkRoleNameUnique(roleName);
    }

    /**
     * 校验权限字符是否唯一
     *
     * @param roleKey
     * @return
     */
    @Override
    public Integer checkRoleKeyUnique(String roleKey) {
        return creditRoleMapper.checkRoleKeyUnique(roleKey);
    }

    /**
     * 保存用户角色
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer saveUserRole(Map map) {
        try {
            return creditUserRoleMapper.saveUserRole(map);
        } catch (Exception e) {
            throw new CustomException("保存用户角色失败");
        }
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    @Override
    public List<CreditMenu> getChildPerms(List<CreditMenu> list, int parentId) {
        List<CreditMenu> returnList = new ArrayList<CreditMenu>();
        for (Iterator<CreditMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            CreditMenu menu = (CreditMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (menu.getParentId() == parentId) {
                recursionFn(list, menu);
                returnList.add(menu);
            }
        }
        return returnList;
    }


    @Override
    public List<CreditRole> getCreditRoleAll() {
        return creditRoleMapper.getCreditRoleAll();
    }

    @Override
    public List<Long> getMinChildIds(List<CreditMenu> list, int parentId) {
        List<Long> menuIds = new ArrayList<Long>();
        for (Iterator<CreditMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            CreditMenu menu = (CreditMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (menu.getParentId() == parentId) {
                recursionFn(list, menu, menuIds);
            }
        }
        return menuIds;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param menu
     */
    private void recursionFn(List<CreditMenu> list, CreditMenu menu) {
        // 得到子节点列表
        List<CreditMenu> childList = getChildList(list, menu);
        if (!CollectionUtils.isEmpty(childList)) {
            menu.setChildren(childList);
        }
        for (CreditMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<CreditMenu> it = childList.iterator();
                while (it.hasNext()) {
                    CreditMenu n = (CreditMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 递归列表获取最底层id
     *
     * @param list
     * @param menu
     */
    private void recursionFn(List<CreditMenu> list, CreditMenu menu, List<Long> menus) {
        // 得到子节点列表
        List<CreditMenu> childList = getChildList(list, menu);
        for (CreditMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<CreditMenu> it = childList.iterator();
                while (it.hasNext()) {
                    CreditMenu n = (CreditMenu) it.next();
                    recursionFn(list, n, menus);
                }
            } else {
                menus.add(tChild.getMenuId());
            }
        }

    }

    /**
     * 得到子节点列表
     */
    private List<CreditMenu> getChildList(List<CreditMenu> list, CreditMenu menu) {
        List<CreditMenu> tlist = new ArrayList<CreditMenu>();
        Iterator<CreditMenu> it = list.iterator();
        while (it.hasNext()) {
            CreditMenu n = (CreditMenu) it.next();
            if (n.getParentId().longValue() == menu.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<CreditMenu> list, CreditMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}

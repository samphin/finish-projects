package com.ryit.credituserserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.CreditMenu;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credituserserver.dao.CreditMenuMapper;
import com.ryit.credituserserver.service.CreditMenuService;
import com.ryit.credituserserver.service.CreditRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author 武汉软艺
 **/
@Service("creditMenuService")
public class CreditMenuServiceImpl implements CreditMenuService {

    @Autowired
    private CreditMenuMapper creditMenuMapper;

    @Autowired
    private CreditRoleService creditRoleService;

    @Override
    public List<CreditMenu> getAllCreditMenu() {
        try {
            List<CreditMenu> menus = creditMenuMapper.getAllCreditMenu();
            List<CreditMenu> menuList = creditRoleService.getChildPerms(menus, 0);
            return menuList;
        } catch (Exception e) {
            throw new CustomException("获取所有菜单异常",e);
        }
    }

    @Override
    public List<CreditMenu> getUserCreditMenu(Long userId) {
        List<CreditMenu> menus = creditMenuMapper.getUserCreditMenu(userId);
        List<CreditMenu> menuList = creditRoleService.getChildPerms(menus, 0);
        return menuList;
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer addCreditMenu(CreditMenu creditMenu) {
        try {
            return creditMenuMapper.addCreditMenu(creditMenu);
        } catch (Exception e) {
            throw new CustomException("添加菜单失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer editCreditMenu(CreditMenu creditMenu) {
        try {
            return creditMenuMapper.editCreditMenu(creditMenu);
        } catch (Exception e) {
            throw new CustomException("修改菜单失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteCreditMenu(Long menuId) {
        try {
            List<CreditMenu> subMenuList = creditMenuMapper.getCreditMenuByParentId(menuId);
            if (!CollectionUtils.isEmpty(subMenuList)) {
                return AjaxResult.error("此菜单有子菜单不能删");
            }
            Integer num = creditMenuMapper.deleteCreditMenu(menuId);
            return AjaxResult.toAjax(num);
        } catch (Exception e) {
            throw new CustomException("删除菜单失败");
        }
    }
}

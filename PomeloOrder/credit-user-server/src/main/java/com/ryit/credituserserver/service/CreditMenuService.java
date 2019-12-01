package com.ryit.credituserserver.service;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.CreditMenu;

import java.util.List;

/**
 * @Author 武汉软艺
 **/
public interface CreditMenuService {

    /**
     *  获取所有菜单栏目
     * @return
     */
    List<CreditMenu> getAllCreditMenu();

    /**
     *  获取用户菜单栏目
     * @param userId
     * @return
     */
    List<CreditMenu> getUserCreditMenu(Long userId);

    /**
     * 添加菜单
     * @param creditMenu
     * @return
     */
    Integer addCreditMenu(CreditMenu creditMenu);

    /**
     *
     * @param creditMenu
     * @return
     */
    Integer editCreditMenu(CreditMenu creditMenu);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    AjaxResult deleteCreditMenu(Long menuId);
}

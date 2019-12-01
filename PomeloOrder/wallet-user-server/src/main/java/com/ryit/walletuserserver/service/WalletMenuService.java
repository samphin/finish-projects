package com.ryit.walletuserserver.service;

import com.ryit.commons.entity.pojo.WalletMenu;
import com.ryit.commons.entity.vo.WalletMenuVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/21 0021下午 3:13
 */
public interface WalletMenuService {

    /**
     * 添加菜单
     *
     * @param walletMenu
     * @return
     */
    Integer insertWalletMenu(WalletMenu walletMenu);

    /**
     * 修改菜单
     *
     * @param walletMenu
     * @return
     */
    Integer updateWalletMenu(WalletMenu walletMenu);

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    Integer deleteWalletMenu(Long menuId);

    /**
     * 查询菜单列表
     *
     * @return
     */
    List<WalletMenuVo> queryMenuList();

    /**
     * 查询菜单详情
     *
     * @param menuId
     * @return
     */
    WalletMenu queryMenuById(Long menuId);

    /**
     * 查询用户的菜单列表
     *
     * @param userId
     * @return
     */
    List<WalletMenuVo> queryUserMenuList(Long userId);
}

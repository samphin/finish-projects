package com.ryit.walletuserserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.BaseQueryDto;
import com.ryit.commons.entity.pojo.WalletMenu;
import com.ryit.commons.entity.vo.WalletMenuVo;
import com.ryit.walletuserserver.service.WalletMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/12 0012下午 7:47
 */
@RestController
@RequestMapping("/walletMenu")
public class WalletMenuController extends BaseController {

    @Autowired
    private WalletMenuService walletMenuService;

    /**
     * 添加菜单
     *
     * @param walletMenu
     * @return
     */
    @PostMapping("/admin/menu")
    public AjaxResult insertWalletMenu(@RequestBody WalletMenu walletMenu) {
        return AjaxResult.success(walletMenuService.insertWalletMenu(walletMenu));
    }

    /**
     * 修改菜单
     *
     * @param walletMenu
     * @return
     */
    @PutMapping("/admin/menu")
    public AjaxResult updateWalletMenu(@RequestBody WalletMenu walletMenu) {
        return AjaxResult.success(walletMenuService.updateWalletMenu(walletMenu));
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @DeleteMapping("/admin/menu/{menuId}")
    public AjaxResult deleteWalletMenu(@PathVariable("menuId") Long menuId) {
        return AjaxResult.success(walletMenuService.deleteWalletMenu(menuId));
    }

    /**
     * 查询菜单列表
     *
     * @return
     */
    @GetMapping("/admin/menu")
    public AjaxResult queryMenuList() {
        List<WalletMenuVo> list = walletMenuService.queryMenuList();
        return AjaxResult.success(list);
    }

    /**
     * 查询菜单详情
     *
     * @param menuId
     * @return
     */
    @GetMapping("/admin/menu/{menuId}")
    public AjaxResult queryMenuById(@PathVariable("menuId") Long menuId) {
        return AjaxResult.success(walletMenuService.queryMenuById(menuId));
    }

    /**
     * 查询用户关联的菜单
     *
     * @param request
     * @return
     */
    @GetMapping("/admin/userMenu")
    public AjaxResult queryUserMenuList(HttpServletRequest request) {
        Long account = getUserId(request);
        return AjaxResult.success(walletMenuService.queryUserMenuList(account));
    }

}

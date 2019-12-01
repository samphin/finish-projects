package com.ryit.credituserserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.pojo.CreditMenu;
import com.ryit.credituserserver.service.CreditMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author 武汉软艺
 **/
@RestController
@RequestMapping("/creditMenu")
public class CreditMenuController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CreditMenuController.class);

    @Autowired
    private CreditMenuService creditMenuService;

    /**
     *  获取所有菜单
     * @return
     */
    @GetMapping("/admin/getAllCreditMenu")
    public AjaxResult getAllCreditMenu(){
        List<CreditMenu> result = creditMenuService.getAllCreditMenu();
        return AjaxResult.success(result);
    }


    /**
     *  获取用户菜单栏目
     * @param request
     * @return
     */
    @GetMapping("/admin/getUserMenu")
    public AjaxResult getUserMenu(HttpServletRequest request){
        List<CreditMenu> result = null;
        try {
            Long account = getUserId(request);
            result = creditMenuService.getUserCreditMenu(account);
            return AjaxResult.success(result);
        } catch (Exception e) {
            log.error("获取所有菜单异常",e);
        }
        return AjaxResult.error();
    }


    /**
     * 添加菜单
     * @param creditMenu
     * @return
     */
    @PostMapping("/admin/addCreditMenu")
    public AjaxResult addCreditMenu(@RequestBody CreditMenu creditMenu){
        Integer num = 0;
        try {
            num = creditMenuService.addCreditMenu(creditMenu);
        } catch (Exception e) {
            log.error("添加菜单异常",e);
        }
        return toAjax(num);
    }

    /**
     * 编辑菜单
     * @param creditMenu
     * @return
     */
    @PostMapping("/admin/editCreditMenu")
    public AjaxResult editCreditMenu(@RequestBody CreditMenu creditMenu){
        Integer num = 0;
        try {
            num = creditMenuService.editCreditMenu(creditMenu);
        } catch (Exception e) {
            log.error("编辑菜单异常",e);
        }
        return toAjax(num);
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @PostMapping("/admin/deleteCreditMenu")
    public AjaxResult deleteCreditMenu(@RequestParam("menuId") Long menuId){
        return creditMenuService.deleteCreditMenu(menuId);
    }

}

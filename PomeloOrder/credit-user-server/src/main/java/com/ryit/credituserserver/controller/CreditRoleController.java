package com.ryit.credituserserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditRoleDto;
import com.ryit.commons.entity.pojo.CreditRole;
import com.ryit.commons.entity.vo.CreditRoleVo;
import com.ryit.credituserserver.service.CreditRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 武汉软艺
 **/
@RestController
@RequestMapping("/creditRole")
public class CreditRoleController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CreditRoleController.class);

    @Autowired
    private CreditRoleService creditRoleService;

    /**
     * 根据条件分页查询角色数据
     * @param creditRoleDto
     * @return
     */
    @GetMapping("/admin/getCreditRoleByPage")
    public AjaxResult getCreditRoleByPage(@ModelAttribute CreditRoleDto creditRoleDto){
        startPage(creditRoleDto.getPageNum(),creditRoleDto.getPageSize(),true);
        List<CreditRole> result = creditRoleService.getCreditRoleByPage(creditRoleDto);
        return AjaxResult.success(getNewPageData(result));
    }

    /**
     * 查询角色详情
     * @param roleId
     * @return
     */
    @GetMapping("/admin/getCreditRoleInfo")
    public AjaxResult getCreditRoleInfo(@RequestParam("roleId") Long roleId){
        CreditRoleVo result = null;
        try {
            result = creditRoleService.getCreditRoleInfo(roleId);
            return AjaxResult.success(result);
        } catch (Exception e) {
            log.error("查询角色详情异常",e);
        }
        return AjaxResult.error();
    }

    /**
     *  添加角色
     * @param creditRoleDto
     * @return
     */
    @PostMapping("/admin/saveRole")
    public AjaxResult saveRole(@RequestBody CreditRoleDto creditRoleDto){
        Integer num = 0;
        try {
             num = creditRoleService.saveRole(creditRoleDto);
        } catch (Exception e) {
            log.error("添加角色异常",e);
        }
        return toAjax(num);
    }

    /**
     *  编辑角色
     * @param creditRoleDto
     * @return
     */
    @PostMapping("/admin/editRole")
    public AjaxResult editRole(@RequestBody CreditRoleDto creditRoleDto){
        Integer num = 0;
        try {
            num = creditRoleService.editRole(creditRoleDto);
        } catch (Exception e) {
            log.error("编辑角色异常",e);
        }
        return toAjax(num);
    }

    /**
     *  删除角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/admin/deleteRole")
    public AjaxResult deleteRole(@RequestParam("roleId") Long roleId){
        try {
            return  creditRoleService.deleteRole(roleId);
        } catch (Exception e) {
            log.error("删除角色异常",e);
        }
        return AjaxResult.error();
    }

    /**
     * 检验角色名称是否唯一
     * @param roleName
     * @return
     */
    @GetMapping("/admin/checkRoleNameUnique")
    public AjaxResult checkRoleNameUnique(@RequestParam("roleName")String roleName){
        Integer num = 0;
        try {
            num = creditRoleService.checkRoleNameUnique(roleName);
        } catch (Exception e) {
            log.error("检验角色名称是否唯一异常",e);
        }
        return AjaxResult.success(num);
    }

    /**
     *  校验权限字符是否唯一
     * @param roleKey
     * @return
     */
    @GetMapping("/admin/checkRoleKeyUnique")
    public AjaxResult checkRoleKeyUnique(@RequestParam("roleKey") String roleKey){
        Integer num = 0;
        try {
            num = creditRoleService.checkRoleKeyUnique(roleKey);
        } catch (Exception e) {
            log.error("校验权限字符是否唯一异常",e);
        }
        return AjaxResult.success(num);
    }


    @PostMapping("/admin/getAllCreditRole")
    public AjaxResult getAllCreditRole(){
        try {
            List<CreditRole> roles = creditRoleService.getCreditRoleAll();
            return AjaxResult.success(roles);
        } catch (Exception e) {
            log.error("获取所有角色异常",e);
        }
        return AjaxResult.error();
    }

}

package com.ryit.walletuserserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.WalletRoleDto;
import com.ryit.commons.entity.vo.WalletRoleVo;
import com.ryit.walletuserserver.checktor.WalletRoleChecktor;
import com.ryit.walletuserserver.service.WalletRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009上午 9:15
 */
@RestController
@RequestMapping("/walletRole")
public class WalletRoleController extends BaseController {

    @Autowired
    private WalletRoleService walletRoleService;


    /**
     * 后台添加新的权限
     *
     * @param walletRole
     * @return
     */
    @PostMapping("/admin/role")
    public AjaxResult insertWalletRole(@RequestBody WalletRoleDto walletRole) {
        WalletRoleChecktor.checkInsertWalletRole(walletRole);
        Boolean flag = walletRoleService.insertWalletRole(walletRole);
        if (flag) {
            return AjaxResult.success(flag);
        }
        return AjaxResult.error();
    }

    /**
     * 后台修改权限
     *
     * @param walletRole
     * @return
     */
    @PutMapping("/admin/role")
    public AjaxResult updateWalletRole(@RequestBody WalletRoleDto walletRole) {
        WalletRoleChecktor.checkUpdateWalletRole(walletRole);
        Boolean flag = walletRoleService.updateWalletRole(walletRole);
        if (flag) {
            return AjaxResult.success(flag);
        }
        return AjaxResult.error();
    }

    /**
     * id查看权限详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/role/{id}")
    public AjaxResult getRoleInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(walletRoleService.getRoleInfo(id));
    }

    /**
     * id删除权限
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/role/{id}")
    public AjaxResult deleteRole(@PathVariable("id") Long id) {
        Boolean flag = walletRoleService.deleteRole(id);
        if (flag) {
            return AjaxResult.success(flag);
        }
        return AjaxResult.error();
    }

    /**
     * 条件查询权限列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/role")
    public AjaxResult queryRoleByCondition(@ModelAttribute WalletRoleDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletRoleVo> result = walletRoleService.queryRoleByCondition(dto);
        return AjaxResult.success(getPageData(result));
    }


    //============================操作用户和权限 ============================//

    /**
     * 添加后台用户权限
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/admin/userRole")
    public AjaxResult insertUserRole(@RequestBody JSONObject jsonObject) {
        Long userId = jsonObject.getLong("userId");
        Long roleId = jsonObject.getLong("roleId");
        if (null == roleId || null == userId) {
            return AjaxResult.success("参数缺失");
        }
        return AjaxResult.success(walletRoleService.insertUserRole(userId, roleId));
    }

    /**
     * 删除后台用户权限
     *
     * @param jsonObject
     * @return
     */
    @DeleteMapping("/admin/userRole")
    public AjaxResult deleteUserRole(@RequestBody JSONObject jsonObject) {
        Long userId = jsonObject.getLong("userId");
        JSONArray jsonArray = jsonObject.getJSONArray("roleIds");
        if (null == jsonArray || null == userId) {
            return AjaxResult.success("参数缺失");
        }
        List<Long> list = jsonArray.toJavaList(Long.class);
        return AjaxResult.success(walletRoleService.deleteUserRole(userId, list));
    }

    /**
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/admin/userRole/{userId}")
    public AjaxResult queryUserRole(@PathVariable("userId") Long userId) {
        return AjaxResult.success(walletRoleService.queryUserRole(userId));
    }

    //============================操作菜单和权限 ============================//

    /**
     * 添加权限关联的菜单
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/admin/roleMenu")
    public AjaxResult insertRoleMenu(@RequestBody JSONObject jsonObject) {
        Long roleId = jsonObject.getLong("roleId");
        JSONArray jsonArray = jsonObject.getJSONArray("menuIds");
        if (null == jsonArray || null == roleId) {
            return AjaxResult.success("参数缺失");
        }
        List<Long> list = jsonArray.toJavaList(Long.class);
        return AjaxResult.success(walletRoleService.insertRoleMenu(roleId, list));
    }


    /**
     * 删除权限关联的菜单
     *
     * @param jsonObject
     * @return
     */
    @DeleteMapping("/admin/roleMenu")
    public AjaxResult deleteRoleMenu(@RequestBody JSONObject jsonObject) {
        Long roleId = jsonObject.getLong("roleId");
        JSONArray jsonArray = jsonObject.getJSONArray("menuIds");
        if (null == jsonArray || null == roleId) {
            return AjaxResult.success("参数缺失");
        }
        List<Long> list = jsonArray.toJavaList(Long.class);
        return AjaxResult.success(walletRoleService.deleteRoleMenu(roleId, list));
    }

    /**
     * 查询权限关联的菜单
     *
     * @param roleId
     * @return
     */
    @GetMapping("/admin/roleMenu/{roleId}")
    public AjaxResult queryRoleMenu(@PathVariable("roleId") Long roleId) {
        return AjaxResult.success(walletRoleService.queryRoleMenu(roleId));
    }
}

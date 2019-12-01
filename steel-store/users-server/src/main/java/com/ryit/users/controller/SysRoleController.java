package com.ryit.users.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.SysRoleDto;
import com.ryit.commons.entity.dto.SysRolePermissionPkDto;
import com.ryit.commons.entity.dto.SysRolePermissionPkQueryDto;
import com.ryit.commons.entity.vo.SysPermissionVo;
import com.ryit.commons.entity.vo.SysRoleVo;
import com.ryit.users.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "SysRoleController", tags = "角色管理接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/roles")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "创建角色信息", httpMethod = "POST", notes = "新增一条角色信息")
    @PostMapping
    public ResponseData save(@RequestBody SysRoleDto dto) {
        boolean bl = sysRoleService.insertSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "修改角色信息", httpMethod = "PATCH", notes = "修改角色信息")
    @PatchMapping
    public ResponseData update(@Validated @RequestBody SysRoleDto dto) {
        boolean bl = sysRoleService.updateByIdSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "删除角色信息", httpMethod = "DELETE", notes = "根据角色ID，删除一条角色信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "角色ID", required = true, dataType = "Integer")
    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        boolean bl = sysRoleService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "查询角色列表", httpMethod = "POST", notes = "分页查询角色列表信息")
    @PostMapping(value = "/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<SysRoleDto> queryDto) {
        PageBean<SysRoleVo> pageList = sysRoleService.queryPageList(queryDto);
        return ResponseData.success().setData(pageList);
    }

    @ApiOperation(value = "查询所有角色", httpMethod = "GET", notes = "查询所有角色")
    @GetMapping(value = "/all")
    public ResponseData queryAll() {
        List<SysRoleVo> pageList = sysRoleService.queryList();
        return ResponseData.success().setData(pageList);
    }

    @ApiOperation(value = "查询角色详情", httpMethod = "GET", notes = "根据角色ID，查询角色详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "角色ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ResponseData queryOne(@PathVariable Integer id) {
        SysRoleVo vo = sysRoleService.queryById(id);
        return ResponseData.success().setData(vo);
    }

    @ApiOperation(value = "给角色绑定权限信息", httpMethod = "POST", notes = "根据角色ID，给角色绑定一个或多个权限信息")
    @PostMapping("/permissions")
    public ResponseData bindPermissions(@Validated @RequestBody SysRolePermissionPkDto dto) {
        boolean bl = this.sysRoleService.bindingPermissions(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "查询角色已拥有的权限信息", httpMethod = "GET", notes = "根据角色ID，查询角色已拥有的权限信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "角色ID", required = true, dataType = "Integer")
    @GetMapping("/{id}/permissions")
    public ResponseData queryHavePermissions(@PathVariable Integer id) {
        List<SysPermissionVo> permissionVoList = this.sysRoleService.queryRoleHavePermissions(id);
        return ResponseData.success().setData(permissionVoList);
    }

    @ApiOperation(value = "查询角色未拥有的权限信息", httpMethod = "GET", notes = "根据角色ID，查询角色未拥有的权限信息")
    @GetMapping("/other/permissions")
    public ResponseData queryHaveNoPermissions(@ModelAttribute SysRolePermissionPkQueryDto dto) {
        List<SysPermissionVo> permissionVoList = this.sysRoleService.queryRoleHaveNoPermissions(dto);
        return ResponseData.success().setData(permissionVoList);
    }
}

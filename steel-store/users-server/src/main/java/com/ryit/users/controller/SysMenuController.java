package com.ryit.users.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.SysMenuDto;
import com.ryit.commons.entity.vo.SysMenuListVo;
import com.ryit.commons.entity.vo.SysMenuVo;
import com.ryit.commons.entity.vo.SysUserMenuVo;
import com.ryit.users.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "SysMenuController", tags = "菜单/控钮管理接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/menus")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "创建菜单信息", httpMethod = "POST", notes = "新增一条菜单记录")
    @PostMapping
    public ResponseData save(@Validated(SysMenuDto.Save.class) @RequestBody SysMenuDto dto) {
        boolean bl = sysMenuService.insertSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "修改菜单信息", httpMethod = "PATCH", notes = "修改菜单信息")
    @PatchMapping
    public ResponseData update(@Validated(SysMenuDto.Update.class) @RequestBody SysMenuDto dto) {
        boolean bl = sysMenuService.updateByIdSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "删除菜单信息", httpMethod = "DELETE", notes = "根据菜单ID，删除菜单一条信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单ID", required = true, dataType = "Integer")
    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable("id") Integer id) {
        boolean bl = sysMenuService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "分页查询菜单列表信息", httpMethod = "POST", notes = "分页查询菜单列表信息")
    @PostMapping(value = "/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<SysMenuDto> queryDto) {
        PageBean<SysMenuVo> pageList = sysMenuService.queryPageList(queryDto);
        return ResponseData.success().setData(pageList);
    }

    @ApiOperation(value = "查询菜单选择框接口", httpMethod = "GET")
    @GetMapping
    public ResponseData queryMenus() {
        List<SysMenuVo> voList = sysMenuService.queryListByCondition(null);
        return ResponseData.success().setData(voList);
    }

    @ApiOperation(value = "查询用户拥有菜单信息", httpMethod = "GET")
    @GetMapping(value = "/users")
    public ResponseData queryUserHaveMenus(HttpServletRequest request) {
        SysUserMenuVo sysUserMenuVo = sysMenuService.queryUserHaveMenus(request);
        return ResponseData.success().setData(sysUserMenuVo);
    }

    @ApiOperation(value = "分配权限查询菜单树状列表", httpMethod = "GET")
    @GetMapping("/tree")
    public ResponseData queryList() {
        List<SysMenuVo> list = sysMenuService.queryList();
        return ResponseData.success().setData(list);
    }

    @ApiOperation(value = "查询菜单详情", httpMethod = "GET", notes = "根据菜单ID，查询菜单详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ResponseData queryOne(@PathVariable("id") Integer id) {
        SysMenuVo vo = sysMenuService.queryById(id);
        return ResponseData.success().setData(vo);
    }

    @ApiOperation(value = "查询菜单绑定的按钮信息", httpMethod = "GET", notes = "根据菜单ID，查询菜单绑定的按钮信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单ID", required = true, dataType = "Integer")
    @GetMapping("/{id}/buttons")
    public ResponseData queryMenuButtons(@PathVariable Integer id) {
        List<SysMenuListVo> buttonVoList = sysMenuService.queryMenuButtons(id);
        return ResponseData.success().setData(buttonVoList);
    }
}

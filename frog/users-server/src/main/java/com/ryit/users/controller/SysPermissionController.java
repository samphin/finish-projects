package com.ryit.users.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.SysPermissionDto;
import com.ryit.commons.entity.vo.SysPermissionVo;
import com.ryit.users.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "SysPermissionController", tags = "权限管理接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/permissions")
public class SysPermissionController {

    @Autowired
    private ISysPermissionService sysPermissionService;

    @ApiOperation(value = "保存权限信息及资源信息", httpMethod = "POST")
    @PostMapping
    public ResponseData save(@Validated(SysPermissionDto.Save.class) @RequestBody SysPermissionDto dto) {
        boolean bl = sysPermissionService.insertSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "修改权限信息", httpMethod = "PATCH")
    @PatchMapping
    public ResponseData update(@Validated(SysPermissionDto.Update.class) @RequestBody SysPermissionDto dto) {
        return save(dto);
    }

    @ApiOperation(value = "启用或禁用权限信息", httpMethod = "PATCH")
    @PatchMapping("/state")
    public ResponseData updateState(@Validated(SysPermissionDto.Status.class) @RequestBody SysPermissionDto dto) {
        boolean bl = sysPermissionService.updateState(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "删除权限信息", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "path", name = "id", value = "权限ID", required = true, dataType = "Integer")
    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        boolean bl = sysPermissionService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "分页查询权限信息", httpMethod = "POST")
    @PostMapping(value = "/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<SysPermissionDto> queryDto) {
        PageBean<SysPermissionVo> pageList = sysPermissionService.queryPageList(queryDto);
        return ResponseData.success().setData(pageList);
    }

    @ApiOperation(value = "查询所有权限信息", httpMethod = "GET")
    @GetMapping
    public ResponseData queryList(@ModelAttribute SysPermissionDto dto) {
        List<SysPermissionVo> list = sysPermissionService.queryListByCondition(dto);
        return ResponseData.success().setData(list);
    }

    @ApiOperation(value = "查询权限详情(包含绑定的资源信息)", httpMethod = "GET")
    @ApiImplicitParam(paramType = "path", name = "id", value = "权限ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ResponseData queryOne(@PathVariable Integer id) {
        SysPermissionVo vo = sysPermissionService.queryById(id);
        return ResponseData.success().setData(vo);
    }
}

package com.ryit.users.controller;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.SysUserCallInfoDto;
import com.ryit.commons.entity.vo.SysUserCallInfoVo;
import com.ryit.users.service.ISysUserCallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "SysUserCallController", tags = "用户回访接口")
@RestController
@RequestMapping(BaseUrlConstants.BASE_API_PREFIX + "/call-info")
public class SysUserCallController {

    @Autowired
    private ISysUserCallService sysUserCallService;

    @ApiOperation(value = "添加用户回访信息", httpMethod = "POST")
    @PostMapping
    public ResponseData save(@RequestBody SysUserCallInfoDto dto) {
        boolean bl = sysUserCallService.insertSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "查询回访记录", httpMethod = "GET")
    @GetMapping
    public ResponseData queryCallRecord(@ModelAttribute SysUserCallInfoDto dto) {
        SysUserCallInfoVo vo = sysUserCallService.queryCallRecord(dto);
        return ResponseData.success().setData(vo);
    }
}

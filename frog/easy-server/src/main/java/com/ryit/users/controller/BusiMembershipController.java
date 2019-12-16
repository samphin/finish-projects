package com.ryit.users.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiMembershipDto;
import com.ryit.commons.entity.vo.BusiMembershipVo;
import com.ryit.users.service.IBusiMembershipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 会员信息Controller
 *
 * @author samphin
 * @since 2019-10-31 11:11:09
 */
@ApiIgnore
@Api(value = "BusiDeliveryAddressController", tags = "会员信息接口")
@RestController
@RequestMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/members")
public class BusiMembershipController {

    @Autowired
    private IBusiMembershipService busiMembershipService;

    /**
     * 查询会员列表
     *
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "查询会员列表", httpMethod = "POST")
    @PostMapping("/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<BusiMembershipDto> queryDto) {
        PageBean<BusiMembershipVo> pageBean = busiMembershipService.queryPageList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询会员详情
     *
     * @author samphin
     * @since 2019-10-31 11:21:26
     */
    @ApiOperation(value = "查询会员详情", httpMethod = "GET")
    @GetMapping("/{userId}")
    public ResponseData queryDetail(@PathVariable("userId") Integer userId) {
        BusiMembershipVo vo = busiMembershipService.queryMemberDetail(userId);
        return ResponseData.success().setData(vo);
    }
}
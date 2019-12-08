package com.ryit.users.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiCompanyDto;
import com.ryit.commons.entity.vo.BusiCompanyVo;
import com.ryit.users.service.IBusiCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 公司信息控制层
 *
 * @author : samphin
 * @since : 2019-10-22 14:32:25
 */
@Api(value = "BusiCompanyController", tags = "公司信息接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/companies")
public class BusiCompanyController implements IBaseController<Long, BusiCompanyDto> {

    @Autowired
    private IBusiCompanyService busiCompanyService;

    /**
     * 提交公司信息
     *
     * @return
     */
    @Override
    @ApiOperation(value = "提交公司信息", httpMethod = "POST")
    @PostMapping
    public ResponseData save(@Validated @RequestBody BusiCompanyDto companyDto, HttpServletRequest request) {
        boolean bl = busiCompanyService.insertSelective(companyDto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 根据用户ID查询公司详情
     *
     * @return
     */
    @ApiOperation(value = "查询公司详情", httpMethod = "GET")
    @GetMapping("/{userId}")
    public ResponseData queryCompanyInfoByUserId(@PathVariable("userId") Integer userId) {
        BusiCompanyVo busiCompanyVo = busiCompanyService.queryCompanyInfoByUserId(userId);
        return ResponseData.success().setData(busiCompanyVo);
    }
}

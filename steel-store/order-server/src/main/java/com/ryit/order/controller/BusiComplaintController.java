package com.ryit.order.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsComplaintDto;
import com.ryit.commons.entity.dto.BusiGoodsComplaintQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsHandlerComplaintDto;
import com.ryit.commons.entity.vo.BusiComplaintVo;
import com.ryit.order.service.IBusiComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 投诉管理
 *
 * @author samphin
 * @since 2019-10-31 17:20:48
 */
@Api(value = "BusiComplaintController", tags = "投诉信息接口")
@RestController
@RequestMapping
public class BusiComplaintController implements IBaseController<Long, BusiGoodsComplaintDto> {

    @Autowired
    private IBusiComplaintService busiComplaintService;

    /**
     * 提交投诉
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "商品投诉", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/complaints")
    public ResponseData save(@Validated(BusiGoodsComplaintDto.Save.class) @RequestBody BusiGoodsComplaintDto dto, HttpServletRequest request) {
        boolean bl = busiComplaintService.saveComplaint(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 处理投诉
     *
     * @param dto
     * @param request
     * @return
     */
    @ApiOperation(value = "处理投诉", httpMethod = "PUT")
    @PutMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/complaints")
    public ResponseData handlerComplaint(@Validated @RequestBody BusiGoodsHandlerComplaintDto dto, HttpServletRequest request) {
        boolean bl = busiComplaintService.handlerComplaint(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除投诉
     *
     * @return
     */
    @Override
    @ApiOperation(value = "删除投诉", httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/complaints/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiComplaintService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 查询我的投诉列表
     *
     * @return
     */
    @ApiOperation(value = "查询我的投诉", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_API_PREFIX + "/complaints/my")
    public ResponseData queryMyComplaints(@RequestBody BaseQueryDto queryDto, HttpServletRequest request) {
        PageBean<BusiComplaintVo> pageBean = busiComplaintService.queryMyComplaints(queryDto, request);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * PC分页查询投诉列表
     *
     * @return
     */
    @ApiOperation(value = "分页查询投诉列表", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/complaints/page")
    public ResponseData queryComplaintList(@RequestBody BaseQueryDto<BusiGoodsComplaintQueryDto> queryDto) {
        PageBean pageBean = busiComplaintService.queryComplaintList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询投诉详情
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "查询投诉详情", httpMethod = "GET")
    @GetMapping({
            BaseUrlConstants.BASE_API_PREFIX + "/complaints/{id}",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/complaints/{id}"
    })
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiComplaintVo vo = busiComplaintService.queryById(id);
        return ResponseData.success().setData(vo);
    }
}

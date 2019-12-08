package com.ryit.goods.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.bson.BusiGoodsComments;
import com.ryit.commons.entity.dto.BusiGoodsCommentsDto;
import com.ryit.commons.entity.dto.BusiGoodsCommentsQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsCommentsReplyDto;
import com.ryit.commons.entity.vo.BusiGoodsCommentsListVo;
import com.ryit.goods.service.IBusiGoodsCommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品评价管理控制器
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsCommentsController", tags = "商品评价信息接口")
@RestController
@RequestMapping
public class BusiGoodsCommentsController {

    @Autowired
    private IBusiGoodsCommentsService busiGoodsCommentsService;

    //=================================APP接口===========================

    /**
     * 新增商品评价
     *
     * @param dto
     * @param request
     * @return
     */
    @ApiOperation(value = "新增商品评价", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_API_PREFIX + "/goods_comments")
    public ResponseData save(@Validated(BusiGoodsCommentsDto.Save.class) @RequestBody BusiGoodsCommentsDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsCommentsService.insertGoodsComments(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 回复商品评价信息
     *
     * @param dto
     * @param request
     * @return
     */
    @ApiOperation(value = "回复商品评价信息", httpMethod = "PUT")
    @PutMapping(BaseUrlConstants.BASE_API_PREFIX + "/goods_comments")
    public ResponseData reply(@Validated @RequestBody BusiGoodsCommentsReplyDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsCommentsService.replayGoodsComments(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * APP查询商品所有评价信息
     *
     * @return
     */
    @ApiOperation(value = "APP查询商品所有评价信息", httpMethod = "GET")
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/goods_comments/{goodsId}")
    public ResponseData queryAllComments(@PathVariable("goodsId") Long goodsId) {
        List<BusiGoodsComments> busiGoodsComments = busiGoodsCommentsService.queryGoodsComments(goodsId);
        return ResponseData.success().setData(busiGoodsComments);
    }

    /**
     * 分页查询所有评价信息
     *
     * @return
     */
    @ApiOperation(value = "APP分页查询商品的评价", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_API_PREFIX + "/goods_comments/page")
    public ResponseData queryAppCommentsList(@RequestBody BaseQueryDto<Long> queryDto) {
        PageBean<BusiGoodsComments> pageData = busiGoodsCommentsService.queryAppCommentsList(queryDto);
        return ResponseData.success().setData(pageData);
    }

    @ApiOperation(value = "查询我的评价", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_API_PREFIX + "/comments/my")
    public ResponseData queryMyComments(@RequestBody BaseQueryDto queryDto, HttpServletRequest request) {
        PageBean<BusiGoodsComments> pageBean = busiGoodsCommentsService.queryMyComments(queryDto, request);
        return ResponseData.success().setData(pageBean);
    }

    //==========================================================PC端接口====================================================

    /**
     * 删除指定商品评价
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除指定商品评价", httpMethod = "DELETE")
    @DeleteMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_comments/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiGoodsCommentsService.deleteGoodsComments(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 分页查询所有评价信息
     *
     * @return
     */
    @ApiOperation(value = "分页查询商品的评价", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_comments/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<BusiGoodsCommentsQueryDto> queryDto) {
        PageBean<BusiGoodsCommentsListVo> pageData = busiGoodsCommentsService.queryPageGoodsComments(queryDto);
        return ResponseData.success().setData(pageData);
    }
}
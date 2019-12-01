package com.ryit.goods.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsDto;
import com.ryit.commons.entity.dto.BusiGoodsPcQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsShelveDto;
import com.ryit.commons.entity.vo.*;
import com.ryit.goods.service.IBusiGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品管理控制器
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsController", tags = "商品信息接口")
@RestController
@RequestMapping
public class BusiGoodsController implements IBaseController<Long, BusiGoodsDto> {

    @Autowired
    private IBusiGoodsService busiGoodsService;

    /**
     * 添加商品
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "添加商品", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods")
    public ResponseData save(@Validated(BusiGoodsDto.Save.class) @RequestBody BusiGoodsDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsService.insertSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 上架｜下架商品
     *
     * @param dto     商品上下架对象
     * @param request
     * @return
     */
    @ApiOperation(value = "上架｜下架商品", httpMethod = "PATCH")
    @PatchMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods/shelve")
    public ResponseData shelve(@RequestBody BusiGoodsShelveDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsService.shelve(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 修改商品信息
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "修改商品信息", httpMethod = "PUT")
    @PutMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods")
    public ResponseData update(@Validated(BusiGoodsDto.Update.class) @RequestBody BusiGoodsDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsService.updateByIdSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除商品图片
     *
     * @author samphin
     * @since 2019-11-18 17:12:04
     */
    @ApiOperation(value = "删除商品图片", httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods/images/{imageId}")
    public ResponseData deleteImages(@PathVariable("imageId") Long imageId) {
        boolean bl = busiGoodsService.deleteImages(imageId, "");
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * APP查询商品分页列表
     *
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "分页查询商品列表", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods/page")
    public ResponseData queryAppGoodsList(@RequestBody BaseQueryDto<BusiGoodsQueryDto> queryDto) {
        PageBean<BusiGoodsAppListVo> pageBean = busiGoodsService.queryAppGoodsList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询PC端商品分页列表
     *
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "分页查询商品列表", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods/page")
    public ResponseData queryGoodsList(@RequestBody BaseQueryDto<BusiGoodsPcQueryDto> queryDto) {
        PageBean<BusiGoodsListVo> pageBean = busiGoodsService.queryGoodsList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "查询商品详情", httpMethod = "GET")
    @GetMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX + "/goods/{id}",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods/{id}"
    })
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiGoodsDetailVo vo = busiGoodsService.queryGoodsDetail(id);
        return ResponseData.success().setData(vo);
    }

    /**
     * 查询商品形状列表
     *
     * @return
     * @author samphin
     * @since 2019-11-15 11:39:06
     */
    @ApiOperation(value = "查询商品形状列表", httpMethod = "GET")
    @GetMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX + "/goods/shape",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods/shape"
    })
    public ResponseData queryGoodsShape() {
        List<BusiGoodsShapeListVo> voList = busiGoodsService.queryGoodsShape();
        return ResponseData.success().setData(voList);
    }

    /**
     * 查询商品分类列表
     *
     * @return
     * @author samphin
     * @since 2019-11-15 11:39:06
     */
    @ApiOperation(value = "查询商品分类列表", httpMethod = "GET")
    @GetMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX + "/goods/brand",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods/brand"
    })
    public ResponseData queryGoodsBrand() {
        List<BusiGoodsBrandListVo> voList = busiGoodsService.queryGoodsBrand();
        return ResponseData.success().setData(voList);
    }

    /**
     * 查询商品横拉版宽度
     *
     * @author samphin
     * @since 2019-11-19 09:44:58
     */
    @ApiOperation(value = "查询商品横拉版宽度", httpMethod = "GET")
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/goods/width/{brandId}")
    public ResponseData queryGoodsShapeWidth(@PathVariable("brandId") Long brandId) {
        List<Double> widths = busiGoodsService.queryGoodsShapeWidth(brandId);
        return ResponseData.success().setData(widths);
    }
    
}
package com.ryit.goods.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsSkuDto;
import com.ryit.commons.entity.vo.BusiGoodsSkuInfoVo;
import com.ryit.commons.entity.vo.BusiGoodsSkuVo;
import com.ryit.goods.service.IBusiGoodsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品规格管理
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsSkuController", tags = "商品规格信息接口")
@RestController
@RequestMapping
public class BusiGoodsSkuController implements IBaseController<Long, BusiGoodsSkuDto> {

    @Autowired
    private IBusiGoodsSkuService busiGoodsSkuService;

    /**
     * 商品规格保存
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "保存规格信息", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_sku")
    public ResponseData save(@Validated(BusiGoodsSkuDto.Save.class) @RequestBody BusiGoodsSkuDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsSkuService.insertSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 修改规格信息
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "修改规格信息", httpMethod = "PUT")
    @PutMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_sku")
    public ResponseData update(@Validated(BusiGoodsSkuDto.Update.class) @RequestBody BusiGoodsSkuDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsSkuService.updateByIdSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除规格信息
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "删除规格信息", httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_sku/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiGoodsSkuService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @Override
    @ApiOperation(value = "分页查询规格列表", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_sku/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<BusiGoodsSkuDto> queryDto) {
        PageBean<BusiGoodsSkuVo> pageBean = busiGoodsSkuService.queryPageList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    @Override
    @ApiOperation(value = "查询规格详情", httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_sku/{id}")
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiGoodsSkuVo vo = busiGoodsSkuService.queryById(id);
        return ResponseData.success().setData(vo);
    }

    //============================================Feign接口===========================================

    /**
     * 批量保存商品规格
     *
     * @param
     * @param request
     * @return
     */
    @ApiIgnore
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods_sku/batch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData saveBatch(@RequestBody List<BusiGoodsSkuDto> dtos, HttpServletRequest request) {
        List<BusiGoodsSkuInfoVo> voList = busiGoodsSkuService.saveSkuCallBack(dtos, request);
        return ResponseData.success().setData(voList);
    }

}
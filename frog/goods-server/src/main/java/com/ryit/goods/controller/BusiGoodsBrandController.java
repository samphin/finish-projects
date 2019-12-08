package com.ryit.goods.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsBrandDto;
import com.ryit.commons.entity.vo.BusiGoodsBrandVo;
import com.ryit.goods.service.IBusiGoodsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.PastOrPresent;
import java.util.List;

/**
 * 商品类别管理
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsBrandController", tags = "商品类别信息接口")
@RestController
@RequestMapping
public class BusiGoodsBrandController implements IBaseController<Long, BusiGoodsBrandDto> {

    @Autowired
    private IBusiGoodsBrandService busiGoodsBrandService;

    @Override
    @ApiOperation(value = "保存商品类别信息",httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_brands")
    public ResponseData save(@Validated(BusiGoodsBrandDto.Save.class) @RequestBody BusiGoodsBrandDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsBrandService.insertSelective(dto,request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @Override
    @ApiOperation(value = "修改商品类别信息",httpMethod = "PUT")
    @PutMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_brands")
    public ResponseData update(@Validated(BusiGoodsBrandDto.Update.class) @RequestBody BusiGoodsBrandDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsBrandService.updateByIdSelective(dto,request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @Override
    @ApiOperation(value = "删除商品类别信息",httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_brands/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiGoodsBrandService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @Override
    @ApiOperation(value = "查询商品类别列表",httpMethod = "GET")
    @GetMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX + "/goods_brands",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_brands"
    })
    public ResponseData queryListByCondition(@ModelAttribute BusiGoodsBrandDto queryParams) {
        List<BusiGoodsBrandVo> voList = busiGoodsBrandService.queryListByCondition(queryParams);
        return ResponseData.success().setData(voList);
    }

    @Override
    @ApiOperation(value = "分页查询商品类别列表",httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_brands/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<BusiGoodsBrandDto> queryDto) {
        PageBean<BusiGoodsBrandVo> pageBean = busiGoodsBrandService.queryPageList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    @Override
    @ApiOperation(value = "查询商品类别详情",httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_brands/{id}")
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiGoodsBrandVo vo = busiGoodsBrandService.queryById(id);
        return ResponseData.success().setData(vo);
    }

    //=================================Feign Api======================================
    @ApiIgnore
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods_brands", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<List<BusiGoodsBrandVo>> queryGoodsBrandList(){
        List<BusiGoodsBrandVo> voList = busiGoodsBrandService.queryListByCondition(null);
        return ResponseData.success().setData(voList);
    }
}
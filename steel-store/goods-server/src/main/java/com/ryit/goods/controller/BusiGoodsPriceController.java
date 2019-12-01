package com.ryit.goods.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsPriceDto;
import com.ryit.commons.entity.dto.BusiGoodsPriceQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsRolledPriceDto;
import com.ryit.commons.entity.dto.BusiGoodsRolledPriceQueryDto;
import com.ryit.commons.entity.vo.BusiGoodsPriceListVo;
import com.ryit.commons.entity.vo.BusiGoodsPriceVo;
import com.ryit.commons.entity.vo.BusiGoodsRolledPriceListVo;
import com.ryit.goods.service.IBusiGoodsPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品价格管理控制器
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsPriceController", tags = "商品价格信息接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_price")
public class BusiGoodsPriceController implements IBaseController<Long, BusiGoodsPriceDto> {

    @Autowired
    private IBusiGoodsPriceService busiGoodsPriceService;

    /**
     * 新增指定商品价格
     * <p>
     * 1、商品价格表中添加一条记录
     * 2、将对应商品表的市场价格字段设置为最新价格
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "新增指定商品价格", httpMethod = "POST")
    @PostMapping
    public ResponseData save(@Validated(BusiGoodsPriceDto.Save.class) @RequestBody BusiGoodsPriceDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsPriceService.insertSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 新增商品废铁价格
     *
     * @param dto
     * @param request
     * @return
     */
    @ApiOperation(value = "新增商品废铁价格", httpMethod = "POST")
    @PostMapping("/scrap_iron")
    public ResponseData saveScrapIronPrice(@RequestBody BusiGoodsRolledPriceDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsPriceService.saveScrapIronPrice(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 查询商品废铁价格
     *
     * @return
     */
    @ApiOperation(value = "查询商品废铁价格列表", httpMethod = "POST")
    @PostMapping("/scrap_iron/page")
    public ResponseData queryScrapIronPriceList(@RequestBody BaseQueryDto<BusiGoodsRolledPriceQueryDto> queryDto) {
        PageBean<BusiGoodsRolledPriceListVo> pageBean = busiGoodsPriceService.queryScrapIronPriceList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询商品列表
     *
     * @param queryParams
     * @return
     */
    @Override
    @ApiOperation(value = "查询所有商品价格", httpMethod = "GET")
    @GetMapping
    public ResponseData queryListByCondition(@RequestBody BusiGoodsPriceDto queryParams) {
        List<BusiGoodsPriceVo> voList = busiGoodsPriceService.queryListByCondition(queryParams);
        return ResponseData.success().setData(voList);
    }

    /**
     * 查询商品分页列表
     *
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "分页查询所有商品价格", httpMethod = "POST")
    @PostMapping("/page")
    public ResponseData queryGoodPriceList(@RequestBody BaseQueryDto<BusiGoodsPriceQueryDto> queryDto) {
        PageBean<BusiGoodsPriceListVo> pageBean = busiGoodsPriceService.queryGoodPriceList(queryDto);
        return ResponseData.success().setData(pageBean);
    }
}
package com.ryit.goods.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyDto;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyUpdateDto;
import com.ryit.commons.entity.vo.BusiGoodsTrolleyVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.goods.service.IBusiGoodsTrolleyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 购物车管理
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsTrolleyController", tags = "购物车信息接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods_trolley")
public class BusiGoodsTrolleyController implements IBaseController<Long, BusiGoodsTrolleyDto> {

    @Autowired
    private IBusiGoodsTrolleyService busiGoodsTrolleyService;

    /**
     * 加入购物车
     *
     * @param trolleyDtos
     * @param request
     * @return
     */
    @ApiOperation(value = "加入购物车", httpMethod = "POST")
    @PostMapping
    public ResponseData addTrolley(@Validated @RequestBody List<BusiGoodsTrolleyDto> trolleyDtos, HttpServletRequest request) {
        boolean bl = busiGoodsTrolleyService.addTrolley(trolleyDtos, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 修改购物车商品购买数量
     *
     * @param dto
     * @param request
     * @return
     */
    @ApiOperation(value = "修改购物车商品购买数量", httpMethod = "PATCH")
    @PatchMapping("/nums")
    public ResponseData updateGoodsNumber(@Validated @RequestBody BusiGoodsTrolleyUpdateDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsTrolleyService.updateTrolleyNum(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除购物车商品
     *
     * @return
     */
    @ApiOperation(value = "删除购物车商品", httpMethod = "DELETE")
    @DeleteMapping("/{ids}")
    public ResponseData delete(@PathVariable("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new CustomException(SystemErrorEnum.TROLLEY_ID_NOT_EMPTY);
        }
        String[] idArray = ids.split(",");
        List<Long> idList = Arrays.stream(idArray).map(Long::new).collect(Collectors.toList());
        boolean bl = busiGoodsTrolleyService.deleteBatch(idList);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 查询我的购物车列表
     *
     * @return
     */
    @ApiOperation(value = "查询我的购物车", httpMethod = "GET")
    @GetMapping
    public ResponseData queryMyTrolley(HttpServletRequest request) {
        List<BusiGoodsTrolleyVo> voList = busiGoodsTrolleyService.queryMyTrolley(request);
        return ResponseData.success().setData(voList);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @ApiOperation(value = "清空购物车", httpMethod = "DELETE")
    @DeleteMapping("/empty")
    public ResponseData empty(HttpServletRequest request) {
        boolean bl = busiGoodsTrolleyService.empty(request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    //=================================Feign接口========================================

    /**
     * 删除指定购物车指定商品
     *
     * @param goodsIdList
     * @param request
     * @return
     */
    @ApiIgnore
    @DeleteMapping("/goods")
    public ResponseData deleteTrolleyGoods(@RequestParam Set<Long> goodsIdList, HttpServletRequest request) {
        boolean bl = busiGoodsTrolleyService.deleteTrolleyGoods(goodsIdList, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }
}
package com.ryit.goods.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsActivitiesDto;
import com.ryit.commons.entity.vo.BusiGoodsActivitiesVo;
import com.ryit.goods.service.IBusiGoodsActivitiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品活动
 *
 * @author samphin
 * @since 2019-10-23 11:51:02
 */
@Api(value = "BusiGoodsActivitiesController", tags = "商品活动信息接口")
@RestController
@RequestMapping
public class BusiGoodsActivitiesController implements IBaseController<Long, BusiGoodsActivitiesDto> {

    @Autowired
    private IBusiGoodsActivitiesService busiGoodsActivitiesService;

    /**
     * 新增商品活动
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "新增商品活动", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_activities")
    public ResponseData save(@Validated(BusiGoodsActivitiesDto.Save.class) @RequestBody BusiGoodsActivitiesDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsActivitiesService.insertSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 修改商品活动信息
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "修改商品活动", httpMethod = "PUT")
    @PutMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_activities")
    public ResponseData update(@Validated(BusiGoodsActivitiesDto.Update.class) @RequestBody BusiGoodsActivitiesDto dto, HttpServletRequest request) {
        boolean bl = busiGoodsActivitiesService.updateByIdSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除活动信息
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "删除商品活动", httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_activities/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiGoodsActivitiesService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * APP查询活动Banner列表
     *
     * @param queryParams
     * @return
     */
    @Override
    @ApiOperation(value = "APP商品活动Banner图片", httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods_activities")
    public ResponseData queryListByCondition(BusiGoodsActivitiesDto queryParams) {
        List<BusiGoodsActivitiesVo> voList = busiGoodsActivitiesService.queryListByCondition(queryParams);
        return ResponseData.success().setData(voList);
    }

    /**
     * 后台活动列表页面
     *
     * @param queryDto
     * @return
     */
    @Override
    @ApiOperation(value = "分页查询商品活动列表", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_activities/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<BusiGoodsActivitiesDto> queryDto) {
        PageBean<BusiGoodsActivitiesVo> pageBean = busiGoodsActivitiesService.queryPageList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "查询商品活动详情", httpMethod = "GET")
    @GetMapping({
            BaseUrlConstants.BASE_API_PREFIX + "/goods_activities/{id}",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/goods_activities/{id}"
    })
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiGoodsActivitiesVo vo = busiGoodsActivitiesService.queryById(id);
        return ResponseData.success().setData(vo);
    }
}
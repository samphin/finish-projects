package com.ryit.users.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiAdvertDto;
import com.ryit.commons.entity.vo.BusiAdvertVo;
import com.ryit.users.service.IBusiAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 广告信息控制层
 *
 * @author : samphin
 * @since : 2019-10-22 14:32:25
 */
@Api(value = "BusiAdvertController", tags = "广告信息接口")
@RestController
@RequestMapping
public class BusiAdvertController implements IBaseController<Long, BusiAdvertDto> {

    @Autowired
    private IBusiAdvertService busiAdvertService;

    /**
     * 保存广告信息
     *
     * @return
     */
    @Override
    @ApiOperation(value = "保存广告信息", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/adverts")
    public ResponseData save(@Validated(BusiAdvertDto.Save.class) @RequestBody BusiAdvertDto busiAdvertDto, HttpServletRequest request) {
        boolean bl = busiAdvertService.insertSelective(busiAdvertDto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 修改广告信息
     *
     * @return
     */
    @Override
    @ApiOperation(value = "修改广告信息", httpMethod = "PUT")
    @PutMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/adverts")
    public ResponseData update(@Validated(BusiAdvertDto.Update.class) @RequestBody BusiAdvertDto busiAdvertDto, HttpServletRequest request) {
        boolean bl = busiAdvertService.updateByIdSelective(busiAdvertDto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除广告
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "删除广告信息", httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/adverts/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiAdvertService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * Banner图片查询
     *
     * @param queryParams
     * @return
     */
    @Override
    @ApiOperation(value = "广告Banner图片查询", httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/adverts")
    public ResponseData queryListByCondition(@RequestBody BusiAdvertDto queryParams) {
        List<BusiAdvertVo> voList = busiAdvertService.queryListByCondition(queryParams);
        return ResponseData.success().setData(voList);
    }

    /**
     * 分页查询广告列表
     *
     * @param queryDto
     * @return
     */
    @Override
    @ApiOperation(value = "分页查询广告列表", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/adverts/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<BusiAdvertDto> queryDto) {
        PageBean<BusiAdvertVo> pageBean = busiAdvertService.queryPageList(queryDto);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询广告详情
     *
     * @return
     */
    @Override
    @ApiOperation(value = "查询广告详情", httpMethod = "GET")
    @GetMapping({
            BaseUrlConstants.BASE_API_PREFIX + "/adverts/{id}",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/adverts/{id}"
    })
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiAdvertVo busiCompanyVo = busiAdvertService.queryById(id);
        return ResponseData.success().setData(busiCompanyVo);
    }
}

package com.ryit.users.controller;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.vo.BusiGoodsBrandVo;
import com.ryit.commons.entity.vo.BusiHomePageAdvertVo;
import com.ryit.commons.entity.vo.BusiHomePageVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.users.feign.IBusiGoodsFeignClient;
import com.ryit.users.service.IBusiAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制层
 *
 * @author : samphin
 * @since : 2019-10-22 14:32:25
 */
@Api(value = "BusiHomePageController", tags = "APP首页接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/home_page")
public class BusiHomePageController {

    @Autowired
    private IBusiAdvertService busiAdvertService;

    @Autowired
    private IBusiGoodsFeignClient busiGoodsFeignClient;

    /**
     * 查询首页广告、活动、商品类别信息
     *
     * @return
     */
    @ApiOperation(value = "查询首页广告、活动、商品类别信息", httpMethod = "GET")
    @GetMapping
    public ResponseData queryHomePage() {
        BusiHomePageVo vo = new BusiHomePageVo();
        List<BusiHomePageAdvertVo> adverts = busiAdvertService.queryAdvertInfo();
        ResponseData<List<BusiGoodsBrandVo>> responseData = busiGoodsFeignClient.queryGoodsBrandList();
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        List<BusiGoodsBrandVo> brands = responseData.getData();
        vo.setAdverts(adverts);
        vo.setBrands(brands);
        return ResponseData.success().setData(vo);
    }
}

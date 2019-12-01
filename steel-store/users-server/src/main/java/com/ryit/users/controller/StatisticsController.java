package com.ryit.users.controller;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiOrderStatisticDto;
import com.ryit.commons.entity.vo.BusiMemberOrderVo;
import com.ryit.users.feign.IBusiOrderFeignClient;
import com.ryit.users.service.IBusiMembershipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计分析
 *
 * @author samphin
 * @since 2019-10-31 11:14:31
 */
@Api(value = "StatisticsController", tags = "统计功能接口")
@RestController
@RequestMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/statistics")
public class StatisticsController {

    @Resource
    private IBusiOrderFeignClient busiOrderFeignClient;

    @Autowired
    private IBusiMembershipService busiMembershipService;

    /**
     * 统计会员数量
     *
     * @return
     */
    @ApiOperation(value = "统计会员数量", httpMethod = "GET")
    @GetMapping("/members")
    public ResponseData queryMembersCount() {
        int count = busiMembershipService.queryCount(null);
        return ResponseData.success().setData(count);
    }

    /**
     * 会员订单排行
     *
     * @return
     */
    @ApiOperation(value = "会员订单排行", httpMethod = "GET")
    @GetMapping("/member/order/top")
    public ResponseData queryMemberOrders() {
        List<BusiMemberOrderVo> voList = busiMembershipService.queryMemberOrderNum();
        return ResponseData.success().setData(voList);
    }

    /**
     * 营业额概括（总营业额、月营业额、日营业额，根据时间筛选）
     */
    @ApiOperation(value = "营业额概括", httpMethod = "GET")
    @GetMapping("/orders/turnover")
    public ResponseData queryOrdersTurnover(@ModelAttribute BusiOrderStatisticDto dto) {
        ResponseData responseData = busiOrderFeignClient.queryOrdersTurnover(dto);
        return responseData;
    }
}

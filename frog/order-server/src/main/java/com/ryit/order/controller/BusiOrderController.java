package com.ryit.order.controller;

import com.alibaba.fastjson.JSON;
import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.vo.BusiOrderDetailVo;
import com.ryit.commons.entity.vo.BusiOrderListVo;
import com.ryit.commons.entity.vo.BusiOrderVo;
import com.ryit.order.service.IBusiOrderService;
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
 * 订单信息
 *
 * @author samphin
 * @since 2019-11-6 13:42:45
 */
@Api(value = "BusiOrderController", tags = "订单信息接口")
@RestController
@RequestMapping
public class BusiOrderController implements IBaseController<Long, BusiOrderDto> {

    @Autowired
    private IBusiOrderService busiOrderService;

    //============================================APP接口=========================================

    /**
     * 提交下单（立即购买、购物车提交订单）
     *
     * @author samphin
     * @since 2019-10-29 11:01:31
     */
    @ApiOperation(value = "提交下单（立即购买、购物车提交订单）", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order")
    public ResponseData submitOrders(@RequestBody BusiSubmitOrdersDto ordersDto, HttpServletRequest request) {
        Long orderId = busiOrderService.submitOrders(ordersDto, request);
        if (null != orderId) {
            return ResponseData.success().setData(orderId);
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 代理下单
     *
     * @author samphin
     * @since 2019-10-29 11:01:31
     */
    @ApiOperation(value = "代理下单", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/order")
    public ResponseData proxySubmitOrders(@RequestBody BusiProxySubmitOrdersDto ordersDto) {
        boolean bl = busiOrderService.proxySubmitOrders(ordersDto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 查询我的订单列表
     *
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "查询我的订单列表", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/my")
    public ResponseData queryMyOrderList(@RequestBody BaseQueryDto<Integer> queryDto, HttpServletRequest request) {
        PageBean pageBean = busiOrderService.queryMyOrderList(queryDto, request);
        return ResponseData.success().setData(pageBean);
    }

    /**
     * 查询我的拼团详情
     * 查询已拼成（和我一起购买相同商品的最近两人的购买商品记录）、待收货、已完成订单信息
     *
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "查询我的拼团详情", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/group/detail")
    public ResponseData queryMyGroupOrderList(@RequestBody BusiOrderGroupDetailDto queryDto) {
        List<BusiOrderListVo> voList = busiOrderService.queryMyGroupOrderList(queryDto);
        return ResponseData.success().setData(voList);
    }

    /**
     * APP订单详情
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "查询订单详情", httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/{id}")
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiOrderDetailVo vo = busiOrderService.queryOrderDetail(id);
        return ResponseData.success().setData(vo);
    }

    /**
     * 上传支付凭证
     *
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiOperation(value = "上传支付凭证", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/certificate")
    public ResponseData uploadPayCertificate(@Validated @RequestBody BusiOrderCertificateDto dto) {
        boolean bl = busiOrderService.uploadPayCertificate(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 彻底删除订单（暂时不用）
     *
     * @param id
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @Override
    @ApiIgnore
    @ApiOperation(value = "彻底删除订单（暂时不用）", httpMethod = "DELETE")
    @DeleteMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiOrderService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 逻辑删除订单信息
     *
     * @param dto
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiOperation(value = "删除订单信息", httpMethod = "PATCH")
    @PatchMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order")
    public ResponseData updateDelStatus(@RequestBody BusiOrderDto dto, HttpServletRequest request) {
        return update(dto, request);
    }

    /**
     * 手动取消订单
     *
     * @param id
     * @return
     * @author samphin
     * @since 2019-10-29 11:04:06
     */
    @ApiOperation(value = "取消订单", httpMethod = "PATCH")
    @PatchMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/{id}/status")
    public ResponseData cancel(@PathVariable("id") Long id) {
        return autoCancel(id);
    }

    /**
     * 订单退款
     * @param busiOrderReturndto
     * @param request
     * @return
     */
    @ApiOperation(value = "订单退款",httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/refund")
    public ResponseData returnOrder(@Validated @RequestBody BusiOrderReturnDto busiOrderReturndto, HttpServletRequest request){
        boolean bl = busiOrderService.insertRefundOrder(busiOrderReturndto,request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }


    //============================PC端管理后台接口 Start====================================

    /**
     * 分页查询订单
     *
     * @param queryDto
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiOperation(value = "分页查询订单", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/order/page")
    public ResponseData queryOrderPageList(@RequestBody BaseQueryDto<BusiOrderQueryDto> queryDto) {
        PageBean<BusiOrderVo> listOrder = busiOrderService.queryOrderList(queryDto);
        return ResponseData.success().setData(listOrder);
    }

    /**
     * 商家修改订单信息
     *
     * @param dto
     * @param request
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @Override
    @ApiOperation(value = "商家修改订单信息", httpMethod = "PATCH")
    @PatchMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/order")
    public ResponseData update(@RequestBody BusiOrderDto dto, HttpServletRequest request) {
        boolean bl = busiOrderService.updateByIdSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 填写发票信息
     *
     * @param dto
     * @param request
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiOperation(value = "填写订单发票信息", httpMethod = "PATCH")
    @PatchMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/order/invoice")
    public ResponseData updateOrderInvoice(@RequestBody BusiOrderInvoiceDto dto, HttpServletRequest request) {
        boolean bl = busiOrderService.updateOrderInvoice(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }


    /**
     * 商家查看订单详情
     *
     * @param id
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiOperation(value = "商家查看订单详情", httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/order/{id}")
    public ResponseData queryOrderDetail(@PathVariable("id") Long id) {
        return queryOne(id);
    }

    //============================PC端管理后台接口 END=============================================

    //=========================Feign接口 Start==========================================

    /**
     * 查询未支付的订单列表
     *
     * @param queryParams
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @Override
    @ApiIgnore
    @ApiOperation(value = "查询未支付的订单列表", httpMethod = "GET")
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/nopay", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData queryListByCondition(@RequestBody BusiOrderDto queryParams) {
        List<BusiOrderVo> voList = busiOrderService.queryListByCondition(queryParams);
        return ResponseData.success().setData(JSON.toJSONString(voList));
    }

    /**
     * 自动取消订单
     *
     * @param id
     * @return
     * @author samphin
     * @since 2019-10-29 11:04:06
     */
    @ApiIgnore
    @PutMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/order/{id}/status")
    public ResponseData autoCancel(@PathVariable("id") Long id) {
        boolean bl = busiOrderService.autoCancel(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 营业额概括（总营业额、月营业额、日营业额，根据时间筛选）
     *
     * @param dto
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiIgnore
    @GetMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/order/turnover", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Double> queryOrdersTurnover(@ModelAttribute BusiOrderStatisticDto dto) {
        Double turnover = busiOrderService.queryTurnover(dto);
        return ResponseData.success().setData(turnover);
    }

    /**
     * 查询用户订单数量
     *
     * @param userId
     * @return
     * @author samphin
     * @since 2019-11-26 13:56:17
     */
    @ApiIgnore
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/{userId}/order/nums", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Integer> queryUserOrderNums(@PathVariable("userId") Integer userId) {
        Integer orderNums = busiOrderService.queryUserOrderNums(userId);
        return ResponseData.success().setData(orderNums);
    }
}

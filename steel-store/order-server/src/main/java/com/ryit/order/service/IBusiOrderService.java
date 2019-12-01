package com.ryit.order.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.vo.BusiOrderDetailVo;
import com.ryit.commons.entity.vo.BusiOrderListVo;
import com.ryit.commons.entity.vo.BusiOrderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IBusiOrderService extends IBaseService<Long, BusiOrderDto, BusiOrderVo> {

    /**
     * 提交订单
     *
     * @param ordersDto
     * @param request
     * @return
     */
    Long submitOrders(BusiSubmitOrdersDto ordersDto, HttpServletRequest request);

    /**
     * 代理下单
     *
     * @param ordersDto
     * @return
     */
    boolean proxySubmitOrders(BusiProxySubmitOrdersDto ordersDto);


    /**
     * 查询我的订单列表
     *
     * @param queryDto
     * @param request
     * @return
     */
    PageBean queryMyOrderList(BaseQueryDto<Integer> queryDto, HttpServletRequest request);

    /**
     * 填写订单发票信息
     *
     * @author samphin
     * @since 2019-11-21 17:48:58
     */
    boolean updateOrderInvoice(BusiOrderInvoiceDto dto, HttpServletRequest request);

    /**
     * 查询订单列表
     *
     * @param queryDto
     * @return
     */
    PageBean queryOrderList(BaseQueryDto<BusiOrderQueryDto> queryDto);

    /**
     * 查询我的拼团详情
     *
     * @param queryDto
     * @return
     */
    List<BusiOrderListVo> queryMyGroupOrderList(BusiOrderGroupDetailDto queryDto);


    /**
     * 查看订单详情
     *
     * @param id
     * @return
     */
    BusiOrderDetailVo queryOrderDetail(Long id);

    /**
     * 自动取消订单
     *
     * @param orderId
     * @return
     */
    boolean autoCancel(Long orderId);


    /**
     * 上传支付凭证
     *
     * @param dto
     * @return
     */
    boolean uploadPayCertificate(BusiOrderCertificateDto dto);

    /**
     * 查询营业额（总营业额、年营业额、月营业额、日营业额，根据时间筛选）
     *
     * @return
     */
    Double queryTurnover(BusiOrderStatisticDto busiOrderStatisticDto);

    /**
     * 查询用户订单数量
     *
     * @param userId
     * @return
     */
    Integer queryUserOrderNums(Integer userId);

    /**
     * 用户退款，插入退款订单
     *
     * @param busiOrderReturndto
     * @return
     */
    boolean insertRefundOrder(BusiOrderReturnDto busiOrderReturndto, HttpServletRequest request);
}

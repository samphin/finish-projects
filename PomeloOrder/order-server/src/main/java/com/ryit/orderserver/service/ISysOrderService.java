package com.ryit.orderserver.service;

import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.*;

import java.util.List;
import java.util.Map;

public interface ISysOrderService {

    List<SysOrderVo> queryHomePageOrderList(SysOrderQueryDto sysOrderQueryDto);

    SysOrderDetailVo queryHomePageOrderDetail(Long id);

    Map<String, Object> countOrder();

    List<SysOrderVo> getOrderListByCreditUser(Long creditUserId);

    SysOrderDetailVo getCreditUserOrderDetail(Long id, Long creditUserId);

    List<SysOrderVo> getBackOrderListByCreditUser(Long creditUserId);

    SysOrderVo getBackOrderDetail(Long id, Long creditUserId);

    Integer operationOrder(SysOrderDto sysOrder);

    Integer grabOrder(SysOrderDto sysOrder);

    Integer applyBackOrder(SysOrderDto sysOrder);

    Integer checkBackOrder(Long id);

    Integer deleteOrderById(List ids);

    /**
     * PC端查看订单详情
     *
     * @author samphin
     * @date 2019-9-3 18:49:56
     */
    SysOrderDetailVo queryOrderDetail(Long id);

    /**
     * 退单审核
     *
     * @param orderBackDto
     * @return
     */
    Boolean reviewBackOrders(OrderBackDto orderBackDto);

    /**
     * PC端查看订单列表
     *
     * @return
     * @author samphin
     * @date 2019-9-4 11:19:08
     */
    List<SysOrderListVo> queryOrderList(SysOrderListQueryDto queryDto);

    /**
     * 查询最近一次抢单时间
     *
     * @author samphin
     * @date 2019-9-6 09:50:12
     */
    String queryLastGrabOrderPayTime(Long orderId, Long userId);

    /**
     * 查询当日提交的订单数量
     *
     * @return
     */
    Integer getTodayOrderCount();

    /**
     * 贷款提交订单
     *
     * @param account  当前用户ID
     * @param sysOrder 订单信息
     * @return
     */
    Boolean saveSysOrder(Long account, SysOrderDto sysOrder);

    /**
     * 修改订单信息
     *
     * @param sysOrderDto
     * @author samphin
     * @date 2019-10-10 17:30:46
     */
    Boolean updateOrder(SysOrderDto sysOrderDto);

    /**
     * 修改订单贷款人信息
     * @author samphin
     * @date 2019-10-14 14:12:29
     */
    Boolean updateOrderWalletor(UpdateOrderWalletorDto updateIdCardDto);

    /**
     * 查询订单贷款人信息
     * @author samphin
     * @2019-10-14 14:41:11
     */
    WalletUser queryOrderWalletor(Long id);

    /**
     * 查询用户在24小时内提交的订单的数量
     *
     * @param phone
     * @return
     */
    Integer queryOrderWithinDay(String phone);

    /**
     * 保存草稿箱
     *
     * @author samphin
     * @date 2019-10-9 19:30:40
     */
    Boolean saveDrafts(Long userId, SysOrderDto sysOrderDto);

    /**
     * 根据订单草稿信息条件查询草稿箱信息
     *
     * @param params
     * @return
     */
    SysOrderTempDetailVo queryDrafts(Map<String,Object> params);

    /**
     * 查询草稿箱列表
     *
     * @author samphin
     * @date 2019-10-10 14:01:44
     */
    List<SysOrderTempListVo> queryOrderDraftsList(SysOrderTempQueryDto queryDto);

    /**
     * 首页查询今日订单量及最大贷款额度
     *
     * @return
     */
    HomePageFieldVo queryMaxQuotaAndOrderCount();
}

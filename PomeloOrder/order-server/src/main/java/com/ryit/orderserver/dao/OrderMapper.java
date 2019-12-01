package com.ryit.orderserver.dao;

import com.ryit.commons.entity.dto.SysOrderListQueryDto;
import com.ryit.commons.entity.dto.SysOrderQueryDto;
import com.ryit.commons.entity.pojo.SysOrder;
import com.ryit.commons.entity.pojo.SysOrderReturn;
import com.ryit.commons.entity.vo.SysOrderDetailVo;
import com.ryit.commons.entity.vo.SysOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper {

    /**
     * APP首页订单查询
     *
     * @param record
     * @author samphin
     * @date 2019-9-23 20:30:11
     */
    List<SysOrderVo> selectHomePageOrderList(SysOrderQueryDto record);

    /**
     * 针对APP未登录时查询订单详情
     *
     * @param id
     * @return
     */
    SysOrderDetailVo selectOrderDetail(Long id);

    Map<String, Object> countOrder();

    List<SysOrderVo> getOrderListByCreditUser(Long creditUserId);

    SysOrderDetailVo getCreditUserOrderDetail(Long id);

    List<SysOrderVo> getBackOrderListByCreditUser(Long creditUserId);

    SysOrderVo getBackOrderDetail(Long id);

    /**
     * 修改订单信息
     *
     * @param record
     * @return
     * @author samphin
     * @date 2019-10-18 09:35:11
     */
    int updateOrder(SysOrder record);

    int deleteBackOrderByOrder(Long orderId);

    int insertBackOrder(SysOrderReturn record);

    Long getOrderCreditUserById(Long id);

    Double getCoinById(Long id);

    SysOrderVo getOrderForBack(Long id);

    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    int deleteOrderById(List ids);

    /**
     * PC端查询订单列表
     *
     * @param queryDto
     * @return
     * @author samphin
     * @date 2019-10-18 09:33:37
     */
    List<SysOrder> selectAllOrders(SysOrderListQueryDto queryDto);

    /**
     * 通过订单ID，查询订单支付人信息
     *
     * @param id
     * @return
     * @author samphin
     * @date 2019-9-4 09:26:59
     */
    SysOrder selectOrderById(@Param("id") Long id);

    /**
     * 查询最近一次抢单时间
     *
     * @param map
     * @return
     * @author samphin
     * @date 2019-9-6 09:49:03
     */
    Date selectLastGrabOrderPayTime(Map map);

    /**
     * 查询今日订单数量
     *
     * @return
     */
    Integer getTodayOrderCount();

    /**
     * 新增订单
     *
     * @param sysOrder
     * @return
     * @author samphin
     * @date 2019-9-24 19:44:20
     */
    Long insertSelective(SysOrder sysOrder);

    /**
     * 查询用户在一天内提交的订单的数量
     *
     * @param phone 用户电话
     * @return
     */
    Integer queryOrderWithinDay(String phone);

}
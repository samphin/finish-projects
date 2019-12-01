package com.ryit.orderserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.*;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.checktor.SysOrderDtoChecktor;
import com.ryit.orderserver.feign.WalletUserFeignClient;
import com.ryit.orderserver.service.ISysOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 刘修
 * @Date : 2019/8/20 11:01
 * @lastUpdator：samphin
 * @lastUpdateTime：2019-10-10 14:39:06
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private ISysOrderService sysOrderService;

    @Resource
    private WalletUserFeignClient walletUserFeignClient;

    /**
     * 首页订单列表
     */
    @GetMapping("/anon/getOrderList")
    public AjaxResult getOrderList(@ModelAttribute SysOrderQueryDto sysOrderQueryDto, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        startPage(page);
        List<SysOrderVo> voList = sysOrderService.queryHomePageOrderList(sysOrderQueryDto);
        return AjaxResult.success(getPageData(voList));
    }

    /**
     * 首页订单详情
     *
     * @author samphin
     * @date 2019-9-23 20:34:28
     */
    @GetMapping("/anon/getOrderDetail")
    public AjaxResult getOrderDetail(@RequestParam Long id) {
        return AjaxResult.success(sysOrderService.queryHomePageOrderDetail(id));
    }

    /**
     * 首页今日订单统计
     */
    @GetMapping("/anon/countOrder")
    public AjaxResult countOrderNum() {
        return AjaxResult.success(sysOrderService.countOrder());
    }

    /**
     * 查看用户订单列表
     */
    @GetMapping("/getOrderListByCreditUser")
    public AjaxResult getOrderListByCreditUser(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        Long account = getUserId(request);
        startPage(page);
        List<SysOrderVo> voList = sysOrderService.getOrderListByCreditUser(account);
        return AjaxResult.success(getPageData(voList));
    }

    /**
     * 查看用户订单详情
     */
    @GetMapping("/getCreditUserOrderDetail")
    public AjaxResult getCreditUserOrderDetail(@RequestParam Long id, HttpServletRequest request) {
        Long account = getUserId(request);
        return AjaxResult.success(sysOrderService.getCreditUserOrderDetail(id, account));
    }

    /**
     * 查看退单列表
     */
    @GetMapping("/getBackOrderListByCreditUser")
    public AjaxResult getBackOrderListByCreditUser(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        Long account = getUserId(request);
        startPage(page);
        List<SysOrderVo> voList = sysOrderService.getBackOrderListByCreditUser(account);
        return AjaxResult.success(getPageData(voList));
    }

    /**
     * 查看退单详情
     */
    @GetMapping("/getBackOrderDetail")
    public AjaxResult getBackOrderDetail(@RequestParam Long id, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            return AjaxResult.success(sysOrderService.getBackOrderDetail(id, account));
        } catch (CustomException e) {
            return AjaxResult.error("查看退单详情失败");
        }
    }

    /**
     * 设置通话状态
     * 设置订单备注
     * 回收订单
     * 完结订单
     */
    @PostMapping("/operationOrder")
    public AjaxResult operationOrder(@RequestBody SysOrderDto sysOrder, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            sysOrder.setCreditUserId(account);
            return toAjax(sysOrderService.operationOrder(sysOrder));
        } catch (CustomException e) {
            return AjaxResult.error("处理订单失败");
        }
    }

    /**
     * 查询当前用户是否抢单，如果有，返回上次抢单时间接口
     *
     * @return
     * @author samphin
     * @date 2019-9-6 09:38:24
     */
    @GetMapping("/queryLastGrabOrderPayTime")
    public AjaxResult queryLastGrabOrderPayTime(@RequestParam("orderId") Long orderId, HttpServletRequest request) {
        Long account = getUserId(request);
        String date = this.sysOrderService.queryLastGrabOrderPayTime(orderId, account);
        return AjaxResult.success("查询成功", date);
    }

    /**
     * 抢单
     * param 订单id
     * param 优惠券couponId
     */
    @PostMapping("/grabOrder")
    public AjaxResult grabOrder(@RequestBody SysOrderDto sysOrder, HttpServletRequest request) {
        try {
            if (!"2".equals(request.getHeader("authFlag"))) {
                log.error("请求抢单错误,未认证！");
                return AjaxResult.error(0, "请先认证！");
            }
            Long account = getUserId(request);
            sysOrder.setCreditUserId(account);
            return toAjax(sysOrderService.grabOrder(sysOrder));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }

    }

    /**
     * 检查能否退单
     */
    @GetMapping("/checkBackOrder")
    public AjaxResult checkBackOrder(@RequestParam Long id, HttpServletRequest request) {
        try {
            return AjaxResult.success(sysOrderService.checkBackOrder(id));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 申请退单
     */
    @PostMapping("/applyBackOrder")
    public AjaxResult applyBackOrder(@RequestBody SysOrderDto sysOrder, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            sysOrder.setCreditUserId(account);
            return toAjax(sysOrderService.applyBackOrder(sysOrder));
        } catch (CustomException e) {
            return AjaxResult.error("申请退单失败");
        }
    }

    /**
     * 查询24小时内用户提交的订单的数量 用于提交订单时进行拦截
     *
     * @param phone
     * @return
     */
    @GetMapping("/queryUserOrderCount")
    public Integer queryOrderWithinDay(String phone) {
        return sysOrderService.queryOrderWithinDay(phone);
    }

    /**
     * 查询当日提交订单数量 (被queryMaxQuotaAndOrderCount替代)
     *
     * @return
     */
    @GetMapping("/todayOrderCount")
    public AjaxResult getTodayOrderCount() {
        return AjaxResult.success(sysOrderService.getTodayOrderCount());
    }

    /**
     * 查询订单数量和最大可借额度
     *
     * @return
     */
    @GetMapping("/anon/orderCountAndMaxQuota")
    public AjaxResult queryMaxQuotaAndOrderCount() {
        return AjaxResult.success(sysOrderService.queryMaxQuotaAndOrderCount());
    }

    /***********************************************PC端接口**************************************/

    /**
     * /anon/insertOrder--->首页提交订单
     * /admin/orders------->管理后台订单保存接口
     *
     * @param sysOrderDto 订单保存对象
     * @author samphin
     * @date 2019-9-18 20:28:20
     */
    @PostMapping({"/anon/insertOrder", "/admin/orders"})
    public AjaxResult saveOrders(@RequestBody SysOrderDto sysOrderDto, HttpServletRequest request) {
        //对用户输入的信息进行校验
        SysOrderDtoChecktor.check(sysOrderDto);
        Long account = 0L;
        //如果从PC端管理后台新增订单，不用获取用户ID
        if (2 == sysOrderDto.getSource()) {
            account = getUserId(request);
        }
        boolean bl = sysOrderService.saveSysOrder(account, sysOrderDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 订单草稿箱保存
     *
     * @param sysOrderDto
     * @author samphin
     * @date 2019-10-9 20:02:08
     */
    @PostMapping("/admin/orders/drafts")
    public AjaxResult saveOrderDrafts(@RequestBody SysOrderDto sysOrderDto, HttpServletRequest request) {
        Long userId = getUserId(request);
        boolean bl = sysOrderService.saveDrafts(userId, sysOrderDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询草稿箱列表
     *
     * @param queryDto
     * @author samphin
     * @date 2019-10-10 14:04:54
     */
    @GetMapping("/admin/orders/drafts")
    public AjaxResult queryOrderDraftsList(@ModelAttribute SysOrderTempQueryDto queryDto) {
        startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        List<SysOrderTempListVo> sysOrderTemps = sysOrderService.queryOrderDraftsList(queryDto);
        return AjaxResult.success(getNewPageData(sysOrderTemps));
    }

    /**
     * 查看订单草稿箱
     *
     * @author samphin
     * @date 2019-10-9 20:02:08
     */
    @GetMapping("/admin/orders/drafts/{id}")
    public AjaxResult queryOrderDrafts(@PathVariable("id") Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        SysOrderTempDetailVo vo = sysOrderService.queryDrafts(params);
        return AjaxResult.success(vo);
    }

    /**
     * 根据手机号查询钱包用户是否存在
     *
     * @param phone
     * @return
     * @TODO
     */
    @GetMapping("/admin/queryUserByPhone/{phone}")
    public AjaxResult queryUserByPhone(@PathVariable("phone") String phone) {
        //根据手机号，查询出订单草稿、资质信息
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        //订单草稿信息
        SysOrderTempDetailVo vo = sysOrderService.queryDrafts(params);
        //贷款用户信息
        WalletUser walletUser = walletUserFeignClient.queryUserByPhone(phone);
        Map<String, Object> respData = new HashMap<>();
        respData.put("userInfo", walletUser);
        respData.put("orderInfo", vo);
        return AjaxResult.success(respData);
    }

    /**
     * 查询所有订单
     *
     * @author samphin
     * @date 2019-9-2 08:59:42
     */
    @GetMapping("/admin/orders")
    public AjaxResult queryOrdersList(@ModelAttribute SysOrderListQueryDto queryDto) {
        startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        List<SysOrderListVo> voList = sysOrderService.queryOrderList(queryDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查询所有退单订单
     *
     * @author samphin
     * @date 2019-9-9 09:40:02
     */
    @GetMapping("/admin/backOrders")
    public AjaxResult queryBackOrdersList(@ModelAttribute SysOrderListQueryDto queryDto) {
        //只查询退单状态订单
        queryDto.setBackStatus(1);
        startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        List<SysOrderListVo> voList = sysOrderService.queryOrderList(queryDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查询订单详情(如果是退单订单，可查看到退单审核图片)
     *
     * @author samphin
     * @date 2019-9-2 08:59:42
     */
    @GetMapping("/admin/orders/{id}")
    public AjaxResult queryOrderDetail(@PathVariable("id") Long id) {
        SysOrderDetailVo orderDetail = sysOrderService.queryOrderDetail(id);
        return AjaxResult.success(orderDetail);
    }

    /**
     * 修改订单信息
     *
     * @author samphin
     * @date 2019-9-24 18:09:25
     */
    @PatchMapping("/admin/orders")
    public AjaxResult updateOrder(@RequestBody SysOrderDto sysOrderDto) {
        boolean bl = this.sysOrderService.updateOrder(sysOrderDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改订单贷款人身份证信息
     *
     * @author samphin
     * @date 2019-10-14 13:38:17
     */
    @PatchMapping("/admin/orders/walletor")
    public AjaxResult updateOrderWalletor(@RequestBody UpdateOrderWalletorDto updateOrderWalletorDto) {
        boolean bl = this.sysOrderService.updateOrderWalletor(updateOrderWalletorDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询订单贷款人身份证信息
     *
     * @author samphin
     * @date 2019-10-14 13:38:17
     */
    @GetMapping("/admin/orders/walletor/{id}")
    public AjaxResult queryOrderWalletor(@PathVariable("id") Long id) {
        WalletUser walletUser = this.sysOrderService.queryOrderWalletor(id);
        return AjaxResult.success(walletUser);
    }

    /**
     * 批量删除订单
     *
     * @author samphin
     * @date 2019-9-2 09:00:37
     */
    @DeleteMapping("/admin/orders")
    public AjaxResult deleteOrders(@RequestParam("ids") String ids) {
        try {
            if (StringUtils.isBlank(ids)) {
                return AjaxResult.error(0, "订单不存在");
            }
            List idList = Arrays.asList(ids.split(","));
            return toAjax(sysOrderService.deleteOrderById(idList));
        } catch (Exception e) {
            log.error("删除订单失败", e);
            return AjaxResult.error(0, "删除订单失败");
        }
    }

    /**
     * 审核退回订单
     *
     * @author samphin
     * @date 2019-9-2 12:01:27
     */
    @PutMapping("/admin/backOrders")
    public AjaxResult reviewBackOrders(@RequestBody OrderBackDto orderBackDto) {
        try {
            Boolean bl = this.sysOrderService.reviewBackOrders(orderBackDto);
            if (bl) {
                //如果退单审核不通过执行成功后，消息提醒
                if (1 == orderBackDto.getIsPass()) {
                    return AjaxResult.success("审核未通过，退单失败");
                }
                return AjaxResult.success("审核已通过，退单成功");
            } else {
                return AjaxResult.error("退单失败");
            }
        } catch (Exception e) {
            log.error("退单失败", e);
            return AjaxResult.error(0, "退单失败");
        }
    }
}

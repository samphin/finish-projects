package com.ryit.creditcouponserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditCouponDto;
import com.ryit.commons.entity.dto.CreditCouponQueryDto;
import com.ryit.commons.entity.dto.CreditGrantCouponDto;
import com.ryit.commons.entity.vo.CreditCouponListVo;
import com.ryit.commons.entity.vo.CreditCouponVo;
import com.ryit.creditcouponserver.checktor.CreditGrantCouponDtoChecktor;
import com.ryit.creditcouponserver.service.ICreditCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 优惠券管理RESTFUL接口
 *
 * @author samphin
 * @date 2019-8-28 16:24:10
 */
@RestController
@RequestMapping("/creditCoupon")
public class CreditCouponController extends BaseController {

    @Autowired
    private ICreditCouponService creditCouponService;

    /**
     * 用户注册领取免单优惠券接口
     *
     * @author samphin
     * @date 2019-8-30 10:51:09
     */
    @PostMapping("/drawFreeOfChargeCoupon")
    public void drawFreeOfChargeCoupon(@RequestBody JSONObject requestData) {
        Long userId = requestData.getLong("userId");
        creditCouponService.drawFreeOfChargeCoupon(userId);
    }

    /**
     * 将优惠券改成已使用状态
     *
     * @author samphin
     * @date 2019-8-31 10:34:09
     */
    @PostMapping("/updateCouponUseStatus")
    public Boolean updateCouponUseStatus(Long relationId) {
        return creditCouponService.updateCouponUseStatus(relationId);
    }

    /**
     * 查询用户当前优惠券可优惠的金额
     *
     * @author samphin
     * @date 2019-8-30 10:51:09
     */
    @GetMapping("/queryDiscountCoin/{relationId}")
    public Double queryDiscountCoin(@PathVariable("relationId") Long relationId) {
        return creditCouponService.queryDiscountCoin(relationId);
    }

    /**
     * 领取优惠券接口
     *
     * @param creditCouponDto
     * @return
     * @author samphin
     * @date 2019-8-30 10:51:09
     */
    @PostMapping("/drawCoupon")
    public AjaxResult drawCoupon(@RequestBody CreditCouponDto creditCouponDto) {
        CreditGrantCouponDto couponDto = new CreditGrantCouponDto();
        couponDto.setModuleId(creditCouponDto.getModuleId());
        couponDto.setUserIds(Arrays.asList(creditCouponDto.getAccount()));
        boolean bl = creditCouponService.drawCoupon(couponDto);
        if (bl) {
            return AjaxResult.success("领取优惠券成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询用户待使用且未过期的优惠券列表
     *
     * @author samphin
     * @date 2019-8-30 10:51:09
     */
    @GetMapping("/myNoUseCouponList")
    public AjaxResult queryMyNoUseCouponList(HttpServletRequest request) {
        Long account = getUserId(request);
        List<CreditCouponVo> voList = creditCouponService.queryNoUseCouponList(account, null);
        return AjaxResult.success(voList);
    }

    /**
     * 查询用户已过期的优惠券列表
     *
     * @author samphin
     * @date 2019-8-30 10:51:09
     */
    @GetMapping("/myPastDueCouponList")
    public AjaxResult queryPastDueCouponList(HttpServletRequest request) {
        Long account = getUserId(request);
        List<CreditCouponVo> voList = creditCouponService.queryPastDueCouponList(account);
        return AjaxResult.success(voList);
    }

    /**
     * 查询订单明细满足条件的优惠券列表
     *
     * @author samphin
     * @date 2019-8-30 10:51:29
     */
    @GetMapping("/orderCouponList")
    public AjaxResult queryOrderCouponList(@RequestParam("coin") Double coin, HttpServletRequest request) {
        Long account = getUserId(request);
        List<CreditCouponVo> voList = creditCouponService.queryNoUseCouponList(account, coin);
        return AjaxResult.success(voList);
    }

    /***************************PC端接品******************************************/
    @GetMapping("/admin/coupons")
    public AjaxResult queryCouponList(@ModelAttribute CreditCouponQueryDto queryDto) {
        startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        List<CreditCouponListVo> voList = creditCouponService.queryCouponList(queryDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 给指定用户发放优惠券
     *
     * @author samphin
     * @date 2019-10-18 09:54:38
     */
    @PostMapping("/admin/coupons")
    public AjaxResult grantFreeCoupons(@RequestBody CreditGrantCouponDto couponDto) {
        CreditGrantCouponDtoChecktor.check(couponDto);
        boolean bl = creditCouponService.drawCoupon(couponDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}

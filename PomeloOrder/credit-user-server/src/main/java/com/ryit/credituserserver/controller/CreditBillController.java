package com.ryit.credituserserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditBillQueryDto;
import com.ryit.commons.entity.enums.BillTypeEnum;
import com.ryit.commons.entity.pojo.CreditBill;
import com.ryit.commons.entity.pojo.CreditRecharge;
import com.ryit.commons.entity.vo.CreditBillListVo;
import com.ryit.commons.entity.vo.CreditBillStatisticsVo;
import com.ryit.commons.entity.vo.CreditBillVo;
import com.ryit.credituserserver.service.CreditBillService;
import com.ryit.credituserserver.service.CreditUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/22 17:36
 */
@RestController
@RequestMapping("/creditBill")
public class CreditBillController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CreditBillController.class);

    @Autowired
    private CreditBillService creditBillService;

    @Autowired
    private CreditUserService creditUserService;

    @PostMapping("/saveBill")
    public Long saveBill(@RequestBody CreditBill creditBill) {
        try {
            return creditBillService.saveBill(creditBill);
        } catch (Exception e) {
            log.error("保存账单错误", e);
            return 0L;
        }
    }

    /**
     * 请求用户账单列表
     */
    @GetMapping("/getBillListByCreditUser")
    public AjaxResult getBillListByCreditUser(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        List<CreditBillVo> voList = new ArrayList<>();
        try {
            Long account = getUserId(request);
            startPage(page);
            voList = creditBillService.getBillListByCreditUser(account);
        } catch (Exception e) {
            log.error("请求用户账单列表错误", e);
        }
        return AjaxResult.success(getPageData(voList));
    }

    /**
     * 请求用户账单统计
     */
    @GetMapping("/getBillCountByCreditUser")
    public AjaxResult getBillCountByCreditUser(HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            return AjaxResult.success(creditBillService.getBillCountByCreditUser(account));
        } catch (Exception e) {
            log.error("请求用户账单统计错误", e);
            return AjaxResult.error(0, "请求用户账单统计错误");
        }
    }

    /**
     * 查询充值方式
     */
    @GetMapping("/getRechargeType")
    public AjaxResult getRechargeType(HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            Integer num = creditUserService.getRechargeNum(account);
            return AjaxResult.success(creditBillService.getRechargeType(num > 0 ? 1 : 0));
        } catch (Exception e) {
            log.error("查询充值方式错误", e);
            return AjaxResult.error(0, "查询充值方式错误");
        }
    }

    /**
     * 充值
     */
    @PostMapping("/rechargeCoin")
    public AjaxResult rechargeCoin(@RequestBody CreditRecharge creditRecharge, HttpServletRequest request) {
        try {
            if (null== creditRecharge|| null == creditRecharge.getId()){
                return AjaxResult.error(0, "参数错误");
            }
            Long account = getUserId(request);
            return AjaxResult.success(creditBillService.rechargeCoin(account, creditRecharge.getId()));
        } catch (Exception e) {
            log.error("查询充值方式错误", e);
            return AjaxResult.error(0, "查询充值方式错误");
        }
    }

    /************************PC端接口********************************/

    /**
     * 查询支付流水
     *
     * @author samphin
     * @date 2019-9-2 13:50:20
     */
    @GetMapping("/admin/getPayBill")
    public CreditBillVo getPayBill(@RequestParam("payId") Long payId) {
        CreditBillVo payBill = creditBillService.getPayBill(payId);
        return payBill;
    }

    /**
     * 获取流水类型下拉框值
     *
     * @author samphin
     * @date 2019-9-2 16:36:01
     */
    @GetMapping("/admin/billTypeList")
    public AjaxResult queryBillTypeList() {
        return AjaxResult.success(BillTypeEnum.queryAllBillType());
    }

    /**
     * 查询订单流水列表
     *
     * @param queryDto
     * @author samphin
     * @date 2019-9-2 15:39:08
     */
    @GetMapping("/admin/billList")
    public AjaxResult queryBillList(@ModelAttribute CreditBillQueryDto queryDto) {
        try {
            startPage(queryDto.getPageNum(),queryDto.getPageSize(),true);
            List<CreditBillListVo> voList = creditBillService.queryAllByCondition(queryDto);
            return AjaxResult.success(getNewPageData(voList));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /********************流水统计接口****************/
    /**
     * 根据年、月、日查询所有账单流水抢单、退单、充值汇总情况
     * @param year
     * @param month
     * @param day
     * @author samphin
     * @date 2019-9-2 20:22:37
     * @return
     */
    @GetMapping("/admin/statistics")
    public AjaxResult queryCreditBillStatistics(String year,String month,String day){
        try {
            CreditBillStatisticsVo creditBillStatisticsVo = this.creditBillService.queryCreditBillStatistics(year, month, day);

            return AjaxResult.success(creditBillStatisticsVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            return AjaxResult.error(e.getMessage());
        }
    }
}

package com.ryit.creditcouponserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditCouponModuleDto;
import com.ryit.commons.entity.vo.CreditCouponModuleVo;
import com.ryit.creditcouponserver.service.ICreditCouponModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券模版管理RESTFUL接口
 *
 * @author samphin
 * @date 2019-9-4 16:03:25
 */
@RestController
@RequestMapping("/creditCouponModule")
public class CreditCouponModuleController extends BaseController {

    @Autowired
    private ICreditCouponModuleService creditCouponModuleService;

    /**
     * 新增优惠券
     * @param creditCouponModuleDto
     * @return
     */
    @PostMapping("/admin/modules")
    public AjaxResult saveCouponModule(@RequestBody CreditCouponModuleDto creditCouponModuleDto) {
        boolean bl = creditCouponModuleService.save(creditCouponModuleDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询优惠券模版列表
     * @param creditCouponModuleDto
     * @return
     */
    @GetMapping("/admin/modules")
    public AjaxResult queryCouponModuleList(@ModelAttribute CreditCouponModuleDto creditCouponModuleDto) {
        startPage(creditCouponModuleDto.getPageNum(),creditCouponModuleDto.getPageSize(),true);
        List<CreditCouponModuleVo> voList = creditCouponModuleService.queryAllByCondition(creditCouponModuleDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查看优惠券模版详情
     * @param id
     * @author samphin
     * @date 2019-9-11 09:43:04
     */
    @GetMapping("/admin/modules/{id}")
    public AjaxResult queryCouponModuleDetail(@PathVariable("id") Long id) {
        return AjaxResult.success(creditCouponModuleService.queryOne(id));
    }

    /**
     * 修改优惠券模版信息
     * @param creditCouponModuleDto
     * @author samphin
     * @date 2019-9-11 09:43:28
     * @return
     */
    @PutMapping("/admin/modules")
    public AjaxResult updateCouponModule(@RequestBody CreditCouponModuleDto creditCouponModuleDto) {
        boolean isDraw = creditCouponModuleService.queryCouponCrawRecord(creditCouponModuleDto.getId());
        if(isDraw){
            return AjaxResult.error(200,"优惠券已被领取不能修改");
        }
        return saveCouponModule(creditCouponModuleDto);
    }

    @DeleteMapping("/admin/modules/{id}")
    public AjaxResult deleteCouponModule(@PathVariable("id") Long id) {
        boolean isDraw = creditCouponModuleService.queryCouponCrawRecord(id);
        if(isDraw){
            return AjaxResult.error(200,"优惠券已被领取不能删除");
        }
        boolean bl = creditCouponModuleService.delete(id);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}

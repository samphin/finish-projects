package com.ryit.creditcouponserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditRechargeDto;
import com.ryit.commons.entity.vo.CreditRechargeVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditcouponserver.service.ICreditRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 充值方案RESTFUL接口
 *
 * @author samphin
 * @date 2019-9-3 11:27:47
 */
@RestController
@RequestMapping("/creditRecharge")
public class CreditRechargeController extends BaseController {

    @Autowired
    private ICreditRechargeService creditRechargeService;

    /**
     * 保存充值方案
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @PostMapping("/admin/recharges")
    public AjaxResult saveRecharge(@RequestBody CreditRechargeDto creditRechargeDto) {
        boolean bl = creditRechargeService.save(creditRechargeDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查看充值方案列表
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @GetMapping("/admin/recharges")
    public AjaxResult queryRechargeList(@ModelAttribute CreditRechargeDto creditRechargeDto) {
        startPage(creditRechargeDto.getPageNum(), creditRechargeDto.getPageSize(), true);
        List<CreditRechargeVo> voList = creditRechargeService.queryAllByCondition(creditRechargeDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查看充值方案详情
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @GetMapping("/admin/recharges/{id}")
    public AjaxResult queryRechargeDetail(@PathVariable("id") Long id) {
        CreditRechargeVo vo = creditRechargeService.queryOne(id);
        return AjaxResult.success(vo);
    }

    /**
     * 调整充值方案信息
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @PutMapping("/admin/recharges")
    public AjaxResult updateRecharge(@RequestBody CreditRechargeDto creditRechargeDto) {
        return saveRecharge(creditRechargeDto);
    }

    /**
     * 删除充值方案信息
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @DeleteMapping("/admin/recharges/{id}")
    public AjaxResult deleteRecharge(@PathVariable("id") Long id) {
        boolean bl = creditRechargeService.delete(id);
        if (bl) {
            return AjaxResult.success("充值方案删除成功");
        } else {
            return AjaxResult.error("充值方案已删除");
        }
    }
}

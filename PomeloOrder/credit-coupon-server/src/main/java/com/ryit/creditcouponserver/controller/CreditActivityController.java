package com.ryit.creditcouponserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditActivityDto;
import com.ryit.commons.entity.vo.CreditActivityBannerVo;
import com.ryit.commons.entity.vo.CreditActivityVo;
import com.ryit.creditcouponserver.checktor.CreditActivityDtoChecktor;
import com.ryit.creditcouponserver.service.ICreditActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动管理RESTFUL接口
 *
 * @author samphin
 * @date 2019-9-4 16:11:00
 */
@RestController
@RequestMapping("/creditActivity")
public class CreditActivityController extends BaseController {

    @Autowired
    private ICreditActivityService creditActivityService;

    /**
     * 查询banner活动信息
     */
    @GetMapping("/anon/list")
    public AjaxResult queryActivityList() {
        List<CreditActivityBannerVo> voList = creditActivityService.queryAllBannerActivity();
        return AjaxResult.success(voList);
    }

    /***********************************PC端接口********************************/
    /**
     * 查看活动信息列表
     *
     * @param creditActivityDto
     * @return
     */
    @GetMapping("/admin/activities")
    public AjaxResult queryList(@ModelAttribute CreditActivityDto creditActivityDto) {
        startPage(creditActivityDto.getPageNum(),creditActivityDto.getPageSize(),true);
        List<CreditActivityVo> voList = creditActivityService.queryAllByCondition(creditActivityDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查看活动详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/activities/{id}")
    public AjaxResult queryActivityDetail(@PathVariable("id") Long id) {
        return AjaxResult.success(creditActivityService.queryOne(id));
    }

    /**
     * 新增活动
     *
     * @param creditActivityDto
     * @return
     */
    @PostMapping("/admin/activities")
    public AjaxResult saveActivity(@RequestBody CreditActivityDto creditActivityDto) {
        CreditActivityDtoChecktor.check(creditActivityDto);
        boolean bl = creditActivityService.save(creditActivityDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 更新活动信息
     *
     * @param creditActivityDto
     * @return
     */
    @PutMapping("/admin/activities")
    public AjaxResult updateActivity(@RequestBody CreditActivityDto creditActivityDto) {
        return saveActivity(creditActivityDto);
    }

    /**
     * 删除活动信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/activities/{id}")
    public AjaxResult deleteActivity(@PathVariable("id") Long id) {
        boolean bl = creditActivityService.delete(id);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}

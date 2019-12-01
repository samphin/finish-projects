package com.ryit.creditcouponserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditInviteDto;
import com.ryit.commons.entity.vo.CreditInviteVo;
import com.ryit.creditcouponserver.service.ICreditInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邀请方案RESTFUL接口
 *
 * @author samphin
 * @date 2019-9-3 11:27:47
 */
@RestController
@RequestMapping("/creditInvite")
public class CreditInviteController extends BaseController {

    @Autowired
    private ICreditInviteService creditInviteService;

    /**
     * 保存邀请方案
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @PostMapping("/admin/invites")
    public AjaxResult saveInvite(@RequestBody CreditInviteDto creditInviteDto) {
        boolean isExistNum = creditInviteService.validateNum(creditInviteDto.getNum());
        if(isExistNum){
            return AjaxResult.error(200,"邀请人数方案已存在");
        }
        boolean bl = creditInviteService.save(creditInviteDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查看邀请方案列表
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @GetMapping("/admin/invites")
    public AjaxResult queryInviteList(@ModelAttribute CreditInviteDto creditInviteDto) {
        List<CreditInviteVo> voList = creditInviteService.queryAllByCondition(creditInviteDto);
        return AjaxResult.success(voList);
    }

    /**
     * 查看邀请方案详情
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @GetMapping("/admin/invites/{id}")
    public AjaxResult queryInviteDetail(@PathVariable("id") Long id) {
        CreditInviteVo creditInviteVo = creditInviteService.queryOne(id);
        return AjaxResult.success(creditInviteVo);
    }

    /**
     * 修改邀请方案信息
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @PutMapping("/admin/invites")
    public AjaxResult updateInvite(@RequestBody CreditInviteDto creditInviteDto) {
        boolean bl = creditInviteService.save(creditInviteDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 删除邀请方案
     *
     * @author samphin
     * @date 2019-9-3 11:28:38
     */
    @DeleteMapping("/admin/invites/{id}")
    public AjaxResult deleteInvite(@PathVariable("id") Long id) {
        boolean bl = creditInviteService.delete(id);
        if (bl) {
            return AjaxResult.success("邀请方案删除成功");
        } else {
            return AjaxResult.error("邀请方案已删除");
        }
    }
}

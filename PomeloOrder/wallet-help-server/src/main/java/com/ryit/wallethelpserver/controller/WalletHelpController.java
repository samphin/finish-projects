package com.ryit.wallethelpserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.BaseQueryDto;
import com.ryit.commons.entity.pojo.WalletHelp;
import com.ryit.wallethelpserver.service.WalletHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006下午 1:57
 */
@RestController
@RequestMapping("/help")
public class WalletHelpController extends BaseController {

    @Autowired
    private WalletHelpService walletHelpService;


    /**
     * 查询钱包帮助问题列表
     *
     * @return
     */
    @GetMapping("/anon/walletHelp")
    public AjaxResult queryWalletHelpList() {
        return AjaxResult.success(walletHelpService.queryWalletHelpList());
    }

    /**
     * 钱包帮助问题id查询答案
     *
     * @param id
     * @return
     */
    @GetMapping("/anon/walletHelp/{id}")
    public AjaxResult queryWalletHelpById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletHelpService.queryWalletHelpById(id));
    }


    /**********************后台接口**********************/

    /**
     * 后台查询钱包帮助问题列表
     *
     * @return
     */
    @GetMapping("/admin/walletHelp")
    public AjaxResult queryHelpList(@ModelAttribute BaseQueryDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletHelp> result = walletHelpService.queryWalletHelps();
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 后台钱包帮助问题id查询答案
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/walletHelp/{id}")
    public AjaxResult queryHelpById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletHelpService.queryWalletHelpById(id));
    }

    /**
     * 后台添加钱包帮助
     *
     * @param walletHelp
     * @return
     */
    @PostMapping("/admin/walletHelp")
    public AjaxResult insertWalletHelp(@RequestBody WalletHelp walletHelp) {
        return AjaxResult.success(walletHelpService.insertWalletHelp(walletHelp));
    }

    /**
     * 批量删除钱包帮助
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/admin/walletHelp")
    public AjaxResult deleteWalletHelp(String ids) {
        Boolean flag = walletHelpService.deleteWalletHelp(ids);
        if (flag) {
            return AjaxResult.success(flag);
        } else {
            return AjaxResult.error("删除钱包帮助失败");
        }
    }

    /**
     * 修改钱包帮助
     *
     * @param walletHelp
     * @return
     */
    @PutMapping("/admin/walletHelp")
    public AjaxResult updateWalletHelp(@RequestBody WalletHelp walletHelp) {
        return AjaxResult.success(walletHelpService.updateWalletHelp(walletHelp));
    }
}

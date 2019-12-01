package com.ryit.wallethelpserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.BaseQueryDto;
import com.ryit.commons.entity.pojo.WalletNotify;
import com.ryit.wallethelpserver.service.WalletNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/9 0009下午 4:12
 */
@RestController
@RequestMapping("/notify")
public class WalletNotifyController extends BaseController {

    @Autowired
    private WalletNotifyService walletNotifyService;

    /**
     * 用户查询banner图
     *
     * @return
     */
    @GetMapping("/anon/walletNotify")
    public AjaxResult queryNotifyList() {
        return AjaxResult.success(walletNotifyService.queryNotify(1));
    }

    /**
     * 查询banner详情
     *
     * @param id
     * @return
     */
    @GetMapping("/anon/walletNotify/{id}")
    public AjaxResult queryWalletNotifyById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletNotifyService.queryNotifyById(id));
    }

    /*******************************后台接口**********************************/

    /**
     * 后台查询banner详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/walletNotify/{id}")
    public AjaxResult queryNotifyById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletNotifyService.queryNotifyById(id));
    }

    /**
     * 条件查询
     *
     * @param showFlag 是否显示(0:否1:是) 不传查询所有
     * @param dto
     * @return
     */
    @GetMapping("/admin/walletNotify")
    public AjaxResult queryNotifyList(@ModelAttribute BaseQueryDto dto, Integer showFlag) {
        startPage(dto.getPageNum(), dto.getPageSize(),true);
        List<WalletNotify> result = walletNotifyService.queryNotify(showFlag);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 后台添加banner
     *
     * @param walletNotify
     * @return
     */
    @PostMapping("/admin/walletNotify")
    public AjaxResult insertNotify(@RequestBody WalletNotify walletNotify) {
        return AjaxResult.success(walletNotifyService.insertWalletNotify(walletNotify));
    }

    /**
     * 后台修改banner
     *
     * @param walletNotify
     * @return
     */
    @PutMapping("/admin/walletNotify")
    public AjaxResult updateNotify(@RequestBody WalletNotify walletNotify) {
        return AjaxResult.success(walletNotifyService.updateWalletNotify(walletNotify));
    }

    /**
     * 后台删除banner
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/walletNotify/{id}")
    public AjaxResult deleteNotify(@PathVariable("id") Long id) {
        return AjaxResult.success(walletNotifyService.deleteWalletNotify(id));
    }

}

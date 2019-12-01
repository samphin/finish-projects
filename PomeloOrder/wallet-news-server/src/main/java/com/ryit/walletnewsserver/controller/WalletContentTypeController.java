package com.ryit.walletnewsserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.pojo.WalletContentType;
import com.ryit.walletnewsserver.service.WalletContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 6:20
 */
@RestController
@RequestMapping("/type")
public class WalletContentTypeController extends BaseController {

    @Autowired
    private WalletContentTypeService walletContentTypeService;

    /**
     * 后台添加新的资讯类型
     *
     * @param walletContentType
     * @return
     */
    @PostMapping("/admin/insertType")
    public AjaxResult insertWalletContentType(@RequestBody WalletContentType walletContentType) {
        return AjaxResult.success(walletContentTypeService.insertWalletContentType(walletContentType));
    }

    /**
     * 后台修改资讯类型
     *
     * @param walletContentType
     * @return
     */
    @PostMapping("/admin/updateType")
    public AjaxResult updateWalletContentType(@RequestBody WalletContentType walletContentType) {
        return AjaxResult.success(walletContentTypeService.updateWalletContentType(walletContentType));
    }

    /**
     * 查询资讯类型列表
     *
     * @return
     */
    @GetMapping("/admin/contentType")
    public AjaxResult queryTypeList() {
        return AjaxResult.success(walletContentTypeService.queryTypeList());
    }

}

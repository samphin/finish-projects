package com.ryit.walletnewsserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.WalletContentDto;
import com.ryit.commons.entity.pojo.WalletContent;
import com.ryit.commons.entity.vo.WalletContentTypeVo;
import com.ryit.commons.entity.vo.WalletContentVo;
import com.ryit.walletnewsserver.checktor.WalletContentChecktor;
import com.ryit.walletnewsserver.service.WalletContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/10 0010下午 4:24
 */
@RestController
@RequestMapping("/content")
public class WalletContentController extends BaseController {

    @Autowired
    private WalletContentService walletContentService;


    //===========================后台接口=============================//

    /**
     * 添加分类资讯
     *
     * @param walletContent
     *
     * @return
     */
    @PostMapping("/admin/insertContent")
    public AjaxResult insertWalletContent(@RequestBody WalletContent walletContent) {
        WalletContentChecktor.insertCheck(walletContent);
        return AjaxResult.success(walletContentService.insertWalletContent(walletContent));
    }

    /**
     * 修改分类资讯
     *
     * @param walletContent
     * @return
     */
    @PostMapping("/admin/updateContent")
    public AjaxResult updateWalletContent(@RequestBody WalletContent walletContent) {
        WalletContentChecktor.updateCheck(walletContent);
        return AjaxResult.success(walletContentService.updateWalletContent(walletContent));
    }

    /**
     * 删除分类资讯
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/deleteContent/{id}")
    public AjaxResult deleteWalletContent(@PathVariable("id") Long id) {
        return AjaxResult.success(walletContentService.deleteWalletContent(id));
    }

    //========================h5接口===========================//

    /**
     * 查询首页资讯
     *
     * @return
     */
    @GetMapping("/anon/queryHomePage")
    public AjaxResult queryHomePageContent() {
        return AjaxResult.success(walletContentService.queryHomePageContent());
    }

    /**
     * 顶级类型查询分类资讯
     *
     * @param typeId
     * @return
     */
    @GetMapping("/anon/queryContent/{typeId}")
    public AjaxResult queryContentByType(@PathVariable("typeId") Long typeId) {
        List<WalletContentTypeVo> result = walletContentService.queryContentByType(typeId);
        return AjaxResult.success(result);
    }

    /**
     * 资讯类型查询资讯列表并分页
     *
     * @param dto
     * @return
     */
    @GetMapping("/anon/queryContent")
    public AjaxResult queryContentByPage(@ModelAttribute WalletContentDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletContentVo> list = walletContentService.queryContentWithPage(dto.getTypeId());
        return AjaxResult.success(getPageData(list));
    }

    /**
     * 查询侧边栏的资讯
     *
     * @param typeId
     * @return
     */
    @GetMapping("/anon/querySidebarContent/{typeId}")
    public AjaxResult querySidebarContent(@PathVariable("typeId") Long typeId) {
        return AjaxResult.success(walletContentService.querySidebarContent(typeId));
    }

    /**
     * 查询资讯详情
     *
     * @param id
     * @return
     */
    @GetMapping("/anon/walletContent/{id}")
    public AjaxResult queryContentById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletContentService.queryContentById(id));
    }

}

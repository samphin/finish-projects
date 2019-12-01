package com.ryit.walletnewsserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.WalletNewsDto;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.commons.entity.vo.WalletNewsVo;
import com.ryit.walletnewsserver.service.WalletNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006上午 9:51
 */
@RestController
@RequestMapping("/news")
public class WalletNewsController extends BaseController {


    @Autowired
    private WalletNewsService walletNewsService;

    /**
     * 查询资讯详情
     *
     * @param id
     * @return
     */
    @GetMapping("/anon/walletNews/{id}")
    public AjaxResult queryNewsById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletNewsService.queryNewsById(id));
    }

    /**
     * 获取热门资讯
     *
     * @return
     */
    @GetMapping("/anon/recommendNews")
    public AjaxResult getRecommendNews(@ModelAttribute WalletNewsDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(),true);
        List<WalletNewsVo> result = walletNewsService.getRecommendNews();
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 查询资讯轮播图
     *
     * @return
     */
    @GetMapping("/anon/bannerNews")
    public AjaxResult getNewsBanner() {
        List<WalletNews> result = walletNewsService.getNewsBanner();
        return AjaxResult.success(result);
    }


    /**
     * 条件查询钱包资讯列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/anon/walletNews")
    public AjaxResult queryNewsByCondition(@ModelAttribute WalletNewsDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(),true);
        List<WalletNewsVo> result = walletNewsService.queryNewsByCondition(dto);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 对热门资讯前十进行分词(后续可能需要做定时任务)
     *
     * @return
     */
    @GetMapping("/checkBuzz")
    public AjaxResult checkBuzzword() {
        return AjaxResult.success(walletNewsService.checkBuzzword());
    }

    /**
     * 查询热词列表
     *
     * @return
     */
    @GetMapping("/anon/buzzword")
    public AjaxResult getBuzzword() {
        return AjaxResult.success(walletNewsService.getBuzzword());
    }

    /**
     * 每次点击热词 为热词的点击量 +1
     *
     * @param buzzword
     * @return
     */
    @GetMapping("/anon/incrBuzzword")
    public AjaxResult incrementBuzzword(String buzzword) {
        return AjaxResult.success(walletNewsService.incrementBuzzword(buzzword));
    }

    /********************后台接口***********************/
    /**
     * 后台查询资讯详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/walletNews/{id}")
    public AjaxResult queryWalletNewsById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletNewsService.queryNewsById(id));
    }

    /**
     * 后台条件查询钱包资讯列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/walletNews")
    public AjaxResult queryWalletNewsByCondition(@ModelAttribute WalletNewsDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(),true);
        List<WalletNewsVo> result = walletNewsService.queryNewsByCondition(dto);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 发布新的钱包资讯
     *
     * @param walletNews
     * @return
     */
    @PostMapping("/admin/walletNews")
    public AjaxResult insertWalletNews(@RequestBody WalletNews walletNews) {
        return AjaxResult.success(walletNewsService.insertWalletNews(walletNews));
    }

    /**
     * 后台逻辑删除钱包资讯
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/admin/walletNews")
    public AjaxResult deleteWalletNews(String ids) {
        Boolean flag = walletNewsService.deleteWalletNews(ids);
        if (flag) {
            return AjaxResult.success(flag);
        } else {
            return AjaxResult.error("删除资讯失败");
        }
    }

    /**
     * 后台修改钱包资讯
     *
     * @param walletNews
     * @return
     */
    @PutMapping("/admin/walletNews")
    public AjaxResult updateWalletNews(@RequestBody WalletNews walletNews) {
        return AjaxResult.success(walletNewsService.updateWalletNews(walletNews));
    }
}

package com.ryit.walletproductserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.BaseQueryDto;
import com.ryit.commons.entity.pojo.WalletTag;
import com.ryit.walletproductserver.service.WalletTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011下午 7:48
 */
@RestController
@RequestMapping("/tag")
public class WalletTagController extends BaseController {

    @Autowired
    private WalletTagService walletTagService;


    /**
     * 后台添加商品标签
     *
     * @param walletTag
     * @return
     */
    @PostMapping("/admin/insertTag")
    public AjaxResult insertWalletTag(@RequestBody WalletTag walletTag) {
        return AjaxResult.success(walletTagService.insertWalletTag(walletTag));
    }

    /**
     * 后台修改商品标签
     *
     * @param walletTag
     * @return
     */
    @PostMapping("/admin/updateTag")
    public AjaxResult updateWalletTag(@RequestBody WalletTag walletTag) {
        return AjaxResult.success(walletTagService.updateWalletTag(walletTag));
    }

    /**
     * 后台删除商品标签
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/deleteTag/{id}")
    public AjaxResult deleteWalletTag(@PathVariable("id") Long id) {
        return AjaxResult.success(walletTagService.deleteWalletTag(id));
    }

    /**
     * 后台查询商品标签列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/getTag")
    public AjaxResult queryWalletTag(@ModelAttribute BaseQueryDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletTag> result = walletTagService.queryWalletTag();
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 查询标签详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/getTag/{id}")
    public AjaxResult queryTagById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletTagService.queryTagById(id));
    }

    /**
     * 为商品添加标签
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/admin/insertProductTag")
    public AjaxResult insertWalletProductTag(@RequestBody JSONObject jsonObject) {
        Long productId = jsonObject.getLong("productId");
        JSONArray jsonArray = jsonObject.getJSONArray("tagIds");
        if (null == productId || null == jsonArray) {
            return AjaxResult.error("参数缺失");
        }
        List<Long> tagIds = jsonArray.toJavaList(Long.class);
        return AjaxResult.success(walletTagService.insertWalletProductTag(productId, tagIds));
    }

    /**
     * 删除商品标签
     *
     * @param jsonObject
     * @return
     */
    @DeleteMapping("/admin/deleteProductTag")
    public AjaxResult deleteWalletProductTag(@RequestBody JSONObject jsonObject) {
        Long productId = jsonObject.getLong("productId");
        JSONArray jsonArray = jsonObject.getJSONArray("tagIds");
        if (null == productId || null == jsonArray) {
            return AjaxResult.error("参数缺失");
        }
        List<Long> tagIds = jsonArray.toJavaList(Long.class);
        return AjaxResult.success(walletTagService.deleteWalletProductTag(productId, tagIds));
    }
}

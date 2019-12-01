package com.ryit.walletmarketserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.WalletLoanMarketQueryDto;
import com.ryit.commons.entity.pojo.WalletLoanMarket;
import com.ryit.commons.entity.vo.WalletLoanMarketVo;
import com.ryit.walletmarketserver.service.WalletLoanMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005下午 2:42
 */
@RestController
@RequestMapping("/loanMarket")
public class WalletLoanMarketController extends BaseController {


    @Autowired
    private WalletLoanMarketService walletLoanMarketService;

    /**
     * 查询钱包超市列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/anon/walletLoanMarket")
    public AjaxResult queryWalletLoanMarketByCondition(@ModelAttribute WalletLoanMarketQueryDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletLoanMarketVo> result = walletLoanMarketService.queryWalletLoanMarketByCondition(dto);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 查询钱包超市详情
     *
     * @param id
     * @return
     */
    @GetMapping("/anon/walletLoanMarket/{id}")
    public AjaxResult queryWalletLoanMarketById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletLoanMarketService.queryWalletLoanMarketById(id));
    }


    /*********************后台接口**************************/


    /**
     * 后台查询钱包超市列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/walletLoanMarket")
    public AjaxResult queryLoanMarketByCondition(@ModelAttribute WalletLoanMarketQueryDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletLoanMarketVo> result = walletLoanMarketService.queryWalletLoanMarketByCondition(dto);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 后台查询钱包超市详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/walletLoanMarket/{id}")
    public AjaxResult queryLoanMarketById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletLoanMarketService.queryWalletLoanMarketById(id));
    }

    /**
     * 添加贷款超市
     *
     * @param walletLoanMarket
     * @return
     */
    @PostMapping("/admin/walletLoanMarket")
    public AjaxResult insertWalletLoanMarket(@RequestBody WalletLoanMarket walletLoanMarket) {
        return AjaxResult.success(walletLoanMarketService.insertWalletLoanMarket(walletLoanMarket));
    }


    /**
     * 修改贷款超市
     *
     * @param record
     * @return
     */
    @PutMapping("/admin/walletLoanMarket")
    public AjaxResult updateWalletLoanMarket(@RequestBody WalletLoanMarket record) {
        Boolean flag = walletLoanMarketService.updateWalletLoanMarket(record);
        if (flag) {
            return AjaxResult.success();
        }
        return AjaxResult.error("修改钱包贷款超市失败");
    }

    /**
     * 批量删除钱包贷款超市
     *
     * @return
     */
    @DeleteMapping("/admin/walletLoanMarket")
    public AjaxResult deleteWalletLoanMarket(String ids) {
        return AjaxResult.success(walletLoanMarketService.deleteWalletLoanMarket(ids));
    }

}

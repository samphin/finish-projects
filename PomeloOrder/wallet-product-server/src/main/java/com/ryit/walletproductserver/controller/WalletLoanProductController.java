package com.ryit.walletproductserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.BaseQueryDto;
import com.ryit.commons.entity.dto.WalletLoanProductDto;
import com.ryit.commons.entity.pojo.WalletLoanProduct;
import com.ryit.commons.entity.vo.WalletLoanProductVo;
import com.ryit.walletproductserver.service.WalletLoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005上午 11:50
 */
@RestController
@RequestMapping("/loanProduct")
public class WalletLoanProductController extends BaseController {

    @Autowired
    private WalletLoanProductService walletLoanProductService;


    /**
     * 查询贷款产品列表
     *
     * @param page
     * @return
     */
    @GetMapping("/anon/walletLoanProduct")
    public AjaxResult queryWalletLoanProductList(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        startPage(page);
        List<WalletLoanProductVo> result = walletLoanProductService.queryWalletLoanProductList(null);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 查询用户申请的商品列表
     *
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/userLoanProduct")
    public AjaxResult queryUserProduct(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        Long id = getUserId(request);
        startPage(page);
        List<WalletLoanProductVo> list = walletLoanProductService.queryUserProduct(id);
        return AjaxResult.success(getPageData(list));
    }

    /**
     * 查询贷款产品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/anon/walletLoanProduct/{id}")
    public AjaxResult queryWalletLoanProductById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletLoanProductService.queryWalletLoanProductById(id));
    }


    /**************************后台接口***********************/
    /**
     * 后台查询贷款产品列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/walletLoanProduct")
    public AjaxResult getLoanProductList(@ModelAttribute WalletLoanProductDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(),true);
        List<WalletLoanProductVo> result = walletLoanProductService.queryWalletLoanProductList(dto);
        return AjaxResult.success(getPageData(result));
    }

    /**
     * 后台查询贷款产品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/walletLoanProduct/{id}")
    public AjaxResult getLoanProductById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletLoanProductService.queryWalletLoanProductById(id));
    }


    /**
     * 后台添加钱包贷款产品
     *
     * @param record
     * @return
     */
    @PostMapping("/admin/walletLoanProduct")
    public AjaxResult insertWalletLoanProduct(@RequestBody WalletLoanProduct record) {
        return AjaxResult.success(walletLoanProductService.insertWalletLoanProduct(record));
    }

    /**
     * 后台修改钱包贷款产品
     *
     * @param record
     * @return
     */
    @PutMapping("/admin/walletLoanProduct")
    public AjaxResult updateWalletLoanProduct(@RequestBody WalletLoanProduct record) {
        Boolean flag = walletLoanProductService.updateWalletLoanProduct(record);
        if (flag) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("修改七一钱包贷款产品失败");
        }
    }

    /**
     * 后台批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/admin/walletLoanProduct")
    public AjaxResult deleteWalletLoanProduct(String ids) {
        return AjaxResult.success(walletLoanProductService.deleteWalletLoanMarket(ids));
    }
}

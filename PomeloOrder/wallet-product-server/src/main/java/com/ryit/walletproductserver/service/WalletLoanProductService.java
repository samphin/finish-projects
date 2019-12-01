package com.ryit.walletproductserver.service;

import com.ryit.commons.entity.dto.WalletLoanProductDto;
import com.ryit.commons.entity.pojo.WalletLoanProduct;
import com.ryit.commons.entity.vo.WalletLoanProductVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005上午 11:43
 */
public interface WalletLoanProductService {

    Long insertWalletLoanProduct(WalletLoanProduct record);

    /**
     * 查询大额产品列表
     *
     * @param dto
     * @return
     */
    List<WalletLoanProductVo> queryWalletLoanProductList(WalletLoanProductDto dto);

    /**
     * 产品id查询产品详情
     *
     * @param id
     * @return
     */
    WalletLoanProductVo queryWalletLoanProductById(Long id);

    /**
     * 修改贷款产品信息
     *
     * @param record
     * @return
     */
    Boolean updateWalletLoanProduct(WalletLoanProduct record);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Integer deleteWalletLoanMarket(String ids);

    /**
     * 用户id查询用户已申请的商品列表
     *
     * @param walletUserId
     * @return
     */
    List<WalletLoanProductVo> queryUserProduct(Long walletUserId);
}

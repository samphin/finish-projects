package com.ryit.walletmarketserver.service;

import com.ryit.commons.entity.dto.WalletLoanMarketQueryDto;
import com.ryit.commons.entity.pojo.WalletLoanMarket;
import com.ryit.commons.entity.vo.WalletLoanMarketVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005下午 2:36
 */
public interface WalletLoanMarketService {

    /**
     * 添加贷款超市
     *
     * @param walletLoanMarket
     * @return
     */
    Long insertWalletLoanMarket(WalletLoanMarket walletLoanMarket);

    /**
     * 条件查询贷款超市列表
     *
     * @param dto
     * @return
     */
    List<WalletLoanMarketVo> queryWalletLoanMarketByCondition(WalletLoanMarketQueryDto dto);

    /**
     * 查询贷款超市详情
     *
     * @param id
     * @return
     */
    WalletLoanMarketVo queryWalletLoanMarketById(Long id);

    /**
     * 修改贷款超市
     *
     * @param record
     * @return
     */
    Boolean updateWalletLoanMarket(WalletLoanMarket record);

    /**
     * 批量删除钱包贷款超市
     *
     * @param ids
     * @return
     */
    Integer deleteWalletLoanMarket(String ids);

}

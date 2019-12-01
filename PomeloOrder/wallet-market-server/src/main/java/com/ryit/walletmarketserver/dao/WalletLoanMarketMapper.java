package com.ryit.walletmarketserver.dao;

import com.ryit.commons.entity.dto.WalletLoanMarketQueryDto;
import com.ryit.commons.entity.pojo.WalletLoanMarket;
import com.ryit.commons.entity.vo.WalletLoanMarketVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixunde
 * @Date: 2019/9/5 0005下午 2:01
 */
@Repository
public interface WalletLoanMarketMapper {

    /**
     * 添加贷款超市
     *
     * @param record
     * @return
     */
    Long insertWalletLoanMarket(WalletLoanMarket record);

    /**
     * 条件查询贷款超市列表
     *
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
     * 批量删除贷款超市
     *
     * @param ids
     * @return
     */
    Integer deleteWalletLoanMarket(@Param("ids") List<Long> ids);

}

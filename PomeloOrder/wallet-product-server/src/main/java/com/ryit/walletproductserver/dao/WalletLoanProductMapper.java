package com.ryit.walletproductserver.dao;

import com.ryit.commons.entity.dto.WalletLoanProductDto;
import com.ryit.commons.entity.pojo.WalletLoanProduct;
import com.ryit.commons.entity.vo.WalletLoanProductVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005上午 11:26
 */
@Repository
public interface WalletLoanProductMapper {

    /**
     * 添加商品
     *
     * @param record
     * @return
     */
    Long insertWalletLoanProduct(WalletLoanProduct record);

    /**
     * 查询商品列表
     *
     * @param dto
     * @return
     */
    List<WalletLoanProductVo> queryWalletLoanProductList(WalletLoanProductDto dto);

    /**
     * id查询商品详情
     *
     * @param id
     * @return
     */
    WalletLoanProductVo queryWalletLoanProductById(Long id);

    /**
     * 修改商品
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
    Integer deleteWalletLoanProduct(@Param("ids") List<Long> ids);

    /**
     * 用户id查询用户已申请的商品列表
     *
     * @param walletUserId
     * @return
     */
    List<WalletLoanProductVo> queryUserProduct(Long walletUserId);
}

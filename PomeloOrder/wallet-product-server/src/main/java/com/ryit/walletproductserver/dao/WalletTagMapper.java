package com.ryit.walletproductserver.dao;

import com.ryit.commons.entity.pojo.WalletTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011下午 7:18
 */
@Repository
public interface WalletTagMapper {

    /**
     * 添加商品标签
     *
     * @param walletTag
     * @return
     */
    Integer insertWalletTag(WalletTag walletTag);

    /**
     * 修改商品标签
     *
     * @param walletTag
     * @return
     */
    Integer updateWalletTag(WalletTag walletTag);

    /**
     * 删除商品标签
     *
     * @param id
     * @return
     */
    Integer deleteWalletTag(@Param("id") Long id);

    /**
     * 查询商品标签列表
     *
     * @return
     */
    List<WalletTag> queryWalletTag();

    /**
     * 为商品添加标签
     *
     * @param productId
     * @param list      商品标签id集合
     * @return
     */
    Integer insertWalletProductTag(@Param("productId") Long productId, @Param("list") List<Long> list);

    /**
     * 删除商品标签
     *
     * @param productId
     * @param list      商品标签id集合
     * @return
     */
    Integer deleteWalletProductTag(@Param("productId") Long productId, @Param("list") List<Long> list);

    /**
     * 商品id查询商品标签
     *
     * @param productId
     * @return
     */
    List<WalletTag> queryByProductId(@Param("productId") Long productId);

    /**
     * 查询标签详情
     *
     * @param id
     * @return
     */
    WalletTag queryTagById(@Param("id") Long id);

    /**
     * 查询商品的标签id的集合
     *
     * @param productId
     * @return
     */
    List<Long> queryTagIdByProductId(@Param("productId") Long productId);
}

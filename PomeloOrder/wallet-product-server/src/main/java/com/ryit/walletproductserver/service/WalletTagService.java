package com.ryit.walletproductserver.service;

import com.ryit.commons.entity.pojo.WalletTag;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011下午 7:40
 */
public interface WalletTagService {

    /**
     * 添加商品标签
     *
     * @param walletTag
     * @return
     */
    Long insertWalletTag(WalletTag walletTag);

    /**
     * 修改商品标签
     *
     * @param walletTag
     * @return
     */
    Long updateWalletTag(WalletTag walletTag);

    /**
     * 删除商品标签
     *
     * @param id
     * @return
     */
    Integer deleteWalletTag(Long id);

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
     * @param tagIds    商品标签id集合
     * @return
     */
    Integer insertWalletProductTag(Long productId, List<Long> tagIds);

    /**
     * 删除商品标签
     *
     * @param productId
     * @param tagIds    商品标签id集合
     * @return
     */
    Integer deleteWalletProductTag(Long productId, List<Long> tagIds);

    /**
     * 查询标签详情
     *
     * @param id
     * @return
     */
    WalletTag queryTagById(Long id);
}

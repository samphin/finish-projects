package com.ryit.walletproductserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.pojo.WalletTag;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletproductserver.dao.WalletTagMapper;
import com.ryit.walletproductserver.service.WalletTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011下午 7:42
 */
@Service
public class WalletTagServiceImpl implements WalletTagService {

    @Autowired
    private WalletTagMapper walletTagMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(WalletTagServiceImpl.class);

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletTag(WalletTag walletTag) {
        try {
            walletTag.setCreateTime(new Date());
            walletTagMapper.insertWalletTag(walletTag);
            return walletTag.getId();
        } catch (Exception e) {
            LOGGER.error("添加钱包商品标签失败", e);
            throw new CustomException("添加钱包商品标签失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long updateWalletTag(WalletTag walletTag) {
        try {
            walletTagMapper.updateWalletTag(walletTag);
            return walletTag.getId();
        } catch (Exception e) {
            LOGGER.error("修改钱包商品标签失败", e);
            throw new CustomException("修改钱包商品标签失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWalletTag(Long id) {
        try {
            return walletTagMapper.deleteWalletTag(id);
        } catch (Exception e) {
            LOGGER.error("删除钱包商品标签失败", e);
            throw new CustomException("删除钱包商品标签失败");
        }
    }

    @Override
    public List<WalletTag> queryWalletTag() {
        return walletTagMapper.queryWalletTag();
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer insertWalletProductTag(Long productId, List<Long> tagIds) {
        try {
            //查询商品已添加的标签
            List<Long> tagList = walletTagMapper.queryTagIdByProductId(productId);
            //去除交集
            tagIds.removeAll(tagList);
            if (tagIds.size() > 0) {
                return walletTagMapper.insertWalletProductTag(productId, tagIds);
            }
            return 0;
        } catch (Exception e) {
            LOGGER.error("钱包商品添加标签失败", e);
            throw new CustomException("钱包商品添加标签失败");
        }
    }

    @Override
    public Integer deleteWalletProductTag(Long productId, List<Long> tagIds) {
        try {
            return walletTagMapper.deleteWalletProductTag(productId, tagIds);
        } catch (Exception e) {
            LOGGER.error("钱包商品删除标签失败", e);
            throw new CustomException("钱包商品删除标签失败");
        }
    }

    @Override
    public WalletTag queryTagById(Long id) {
        return walletTagMapper.queryTagById(id);
    }
}

package com.ryit.wallethelpserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.pojo.WalletNotify;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.wallethelpserver.dao.WalletNotifyMapper;
import com.ryit.wallethelpserver.service.WalletNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/9 0009下午 4:09
 */
@Service
public class WalletNotifyServiceImpl implements WalletNotifyService {

    private Logger log = LoggerFactory.getLogger(WalletNotifyServiceImpl.class);

    @Autowired
    private WalletNotifyMapper walletNotifyMapper;

    @Override
    public List<WalletNotify> queryNotify(Integer showFlag) {
        try {
            return walletNotifyMapper.queryNotify(showFlag);
        } catch (Exception e) {
            log.error("查询广告图列表失败", e);
            throw new CustomException("查询广告图列表失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public int insertWalletNotify(WalletNotify walletNotify) {
        try {
            return walletNotifyMapper.insertWalletNotify(walletNotify);
        } catch (Exception e) {
            log.error("后台添加广告图失败", e);
            throw new CustomException("后台添加广告图失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public int updateWalletNotify(WalletNotify walletNotify) {
        try {
            return walletNotifyMapper.updateWalletNotify(walletNotify);
        } catch (Exception e) {
            log.error("后台修改广告图失败", e);
            throw new CustomException("后台修改广告图失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public int deleteWalletNotify(Long id) {
        try {
            return walletNotifyMapper.deleteWalletNotify(id);
        } catch (Exception e) {
            log.error("后台删除广告图失败", e);
            throw new CustomException("后台删除广告图失败");
        }
    }

    @Override
    public WalletNotify queryNotifyById(Long id) {
        try {
            return walletNotifyMapper.queryNotifyById(id);
        } catch (Exception e) {
            log.error("查询广告图详情失败", e);
            throw new CustomException("查询广告图详情失败");
        }
    }
}

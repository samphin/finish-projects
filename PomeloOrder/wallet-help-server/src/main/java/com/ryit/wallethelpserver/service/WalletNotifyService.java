package com.ryit.wallethelpserver.service;

import com.ryit.commons.entity.pojo.WalletNotify;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/9 0009下午 4:08
 */
public interface WalletNotifyService {

    /**
     * 条件查询banner列表
     *
     * @param showFlag 是否显示(0:否1:是) 不传查询所有
     * @return
     */
    List<WalletNotify> queryNotify(Integer showFlag);

    /**
     * 后台添加广告图
     *
     * @param walletNotify
     * @return
     */
    int insertWalletNotify(WalletNotify walletNotify);

    /**
     * 后台修改广告图
     *
     * @param walletNotify
     * @return
     */
    int updateWalletNotify(WalletNotify walletNotify);

    /**
     * 后台删除广告图
     *
     * @param id
     * @return
     */
    int deleteWalletNotify(Long id);

    /**
     * 查询广告图详情
     *
     * @param id
     * @return
     */
    WalletNotify queryNotifyById(Long id);
}

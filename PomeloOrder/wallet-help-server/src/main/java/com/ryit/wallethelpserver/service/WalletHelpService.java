package com.ryit.wallethelpserver.service;

import com.ryit.commons.entity.pojo.WalletHelp;
import com.ryit.commons.entity.vo.WalletHelpVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006下午 1:51
 */
public interface WalletHelpService {

    /**
     * 添加帮助
     *
     * @param walletHelp
     * @return
     */
    Integer insertWalletHelp(WalletHelp walletHelp);

    /**
     * 查询钱包帮助列表及客服电话
     *
     * @return
     */
    WalletHelpVo queryWalletHelpList();

    /**
     * 查询钱包帮助列表
     * @return
     */
    List<WalletHelp> queryWalletHelps();

    /**
     * 查看帮助详情
     *
     * @param id
     * @return
     */
    WalletHelp queryWalletHelpById(Long id);

    /**
     * 批量删除帮助
     *
     * @param ids
     * @return
     */
    Boolean deleteWalletHelp(String ids);

    /**
     * 修改帮助
     *
     * @param walletHelp
     * @return
     */
    Integer updateWalletHelp(WalletHelp walletHelp);

}

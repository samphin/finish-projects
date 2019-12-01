package com.ryit.walletnewsserver.service;

import com.ryit.commons.entity.pojo.WalletContent;
import com.ryit.commons.entity.vo.WalletContentTypeVo;
import com.ryit.commons.entity.vo.WalletContentVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/10 0010上午 10:21
 */
public interface WalletContentService {

    /**
     * 添加分类资讯
     *
     * @param walletContent
     * @return
     */
    Long insertWalletContent(WalletContent walletContent);

    /**
     * 修改分类资讯
     *
     * @param walletContent
     * @return
     */
    Long updateWalletContent(WalletContent walletContent);

    /**
     * 删除分类资讯(逻辑删除)
     *
     * @param id
     * @return
     */
    Integer deleteWalletContent(Long id);

    /**
     * 查询首页的资讯
     *
     * @return
     */
    List<WalletContentTypeVo> queryHomePageContent();

    /**
     * 根据类型查询资讯
     *
     * @param typeId
     * @return
     */
    List<WalletContentTypeVo> queryContentByType(Long typeId);

    /**
     * 查询贷款问答资讯列表
     *
     * @return
     */
    List<WalletContentVo> queryContentWithPage(Long typeId);

    /**
     * 查询侧边栏资讯
     *
     * @param typeId
     * @return
     */
    List<WalletContentTypeVo> querySidebarContent(Long typeId);

    /**
     * 查询资讯详情
     *
     * @param id
     * @return
     */
    WalletContentVo queryContentById(Long id);
}

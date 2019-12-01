package com.ryit.walletnewsserver.service;

import com.ryit.commons.entity.dto.WalletNewsDto;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.commons.entity.vo.WalletNewsVo;

import java.util.List;
import java.util.Set;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006上午 9:45
 */
public interface WalletNewsService {

    /**
     * 添加资讯信息
     *
     * @param walletNews
     * @return
     */
    Long insertWalletNews(WalletNews walletNews);

    /**
     * 批量删除新闻(逻辑删除)
     *
     * @param ids
     * @return
     */
    Boolean deleteWalletNews(String ids);

    /**
     * 查询新闻详情
     *
     * @param id
     * @return
     */
    WalletNewsVo queryNewsById(Long id);

    /**
     * 条件查询新闻列表
     *
     * @param dto
     * @return
     */
    List<WalletNewsVo> queryNewsByCondition(WalletNewsDto dto);

    /**
     * 修改新闻
     *
     * @param walletNews
     * @return
     */
    Long updateWalletNews(WalletNews walletNews);

    /**
     * 获取热门资讯
     *
     * @return
     */
    List<WalletNewsVo> getRecommendNews();

    /**
     * 获取资讯轮播图
     *
     * @return
     */
    List<WalletNews> getNewsBanner();

    /**
     * 筛选热门资讯并进行分词保存
     *
     * @return
     */
    Set<String> checkBuzzword();

    /**
     * 获取热词
     *
     * @return
     */
    Set<Object> getBuzzword();


    /**
     * 热词点击量添加
     *
     * @return
     */
    Long incrementBuzzword(String buzzword);

}

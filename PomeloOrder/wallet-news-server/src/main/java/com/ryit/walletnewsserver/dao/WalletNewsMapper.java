package com.ryit.walletnewsserver.dao;

import com.ryit.commons.entity.dto.WalletNewsDto;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.commons.entity.vo.WalletNewsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006上午 9:03
 */
@Repository
public interface WalletNewsMapper {

    /**
     * 添加钱包资讯
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
    Integer deleteWalletNews(@Param("ids") List<Long> ids);

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
     * 修改钱包资讯
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
     * 查询资讯轮播图
     *
     * @return
     */
    List<WalletNews> getNewsBanner();

    /**
     * 查询热门资讯的标题
     *
     * @param set 热门资讯id
     * @return
     */
    List<String> getRecommendNewsTitle(@Param("set") Set<Object> set);

}

package com.ryit.walletnewsserver.dao;

import com.ryit.commons.entity.pojo.WalletContent;
import com.ryit.commons.entity.vo.WalletContentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/10 0010上午 9:10
 */
@Repository
public interface WalletContentMapper {

    /**
     * 添加分类资讯
     *
     * @param walletContent
     * @return
     */
    Integer insertWalletContent(WalletContent walletContent);

    /**
     * 修改分类资讯
     *
     * @param walletContent
     * @return
     */
    Integer updateWalletContent(WalletContent walletContent);

    /**
     * id查询分类资讯详情
     *
     * @param id
     * @return
     */
    WalletContentVo queryById(@Param("id") Long id);

    /**
     * 删除分类资讯(逻辑删除)
     *
     * @param id
     * @return
     */
    Integer deleteWalletContent(@Param("id") Long id);

    /**
     * 查询顶级id查询分类资讯 只查8条 用于h5首页
     *
     * @param parentTypeId
     * @return
     */
    List<WalletContentVo> queryContentListByParentId(@Param("parentTypeId") Long parentTypeId);

    /**
     * 分类id查询分类资讯
     *
     * @param typeId
     * @return
     */
    List<WalletContentVo> queryContentListByTypeId(@Param("typeId") Long typeId);

    /**
     * 资讯标题查询资讯
     *
     * @param newsTitle
     * @return
     */
    List<WalletContentVo> queryByNewsTitle(@Param("newsTitle") String newsTitle);

    /**
     * 查询简略的资讯
     *
     * @param typeId
     * @return
     */
    List<WalletContentVo> querySimpleContent(@Param("typeId") Long typeId);

    /**
     * 分类id或顶级分类id查询分类资讯
     *
     * @param typeId
     * @return
     */
    List<WalletContentVo> queryContentsByTypeId(@Param("typeId") Long typeId);
}

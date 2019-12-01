package com.ryit.walletnewsserver.dao;

import com.ryit.commons.entity.pojo.WalletContentType;
import com.ryit.commons.entity.vo.WalletContentTypeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 3:52
 */
@Repository
public interface WalletContentTypeMapper {

    /**
     * 修改资讯类型
     *
     * @param walletContentType
     * @return
     */
    Integer updateWalletContentType(WalletContentType walletContentType);

    /**
     * 添加资讯类型
     *
     * @param walletContentType
     * @return
     */
    Integer insertWalletContentType(WalletContentType walletContentType);

    /**
     * 查询资讯类型列表
     *
     * @param level
     * @param homeDisplayFlag 首页是否展示
     * @return
     */
    List<WalletContentTypeVo> queryTypeList(@Param("level") Integer level, @Param("homeDisplayFlag") Integer homeDisplayFlag);

    /**
     * 顶级分类id查询子分类
     *
     * @param topParentId
     * @return
     */
    List<WalletContentTypeVo> queryTypeByTopParentId(@Param("topParentId") Long topParentId);

    /**
     * id查询分类详情
     *
     * @param id
     * @return
     */
    WalletContentTypeVo queryById(@Param("id") Long id);

    /**
     * 查询侧边栏的资讯类型
     *
     * @param typeId
     * @return
     */
    List<WalletContentTypeVo> querySidebarType(@Param("typeId") Long typeId);
}

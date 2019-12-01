package com.ryit.walletnewsserver.service;

import com.ryit.commons.entity.pojo.WalletContentType;
import com.ryit.commons.entity.vo.WalletContentTypeVo;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 4:09
 */
public interface WalletContentTypeService {

    /**
     * 修改资讯类型
     *
     * @param walletContentType
     * @return
     */
    Long updateWalletContentType(WalletContentType walletContentType);

    /**
     * 添加资讯类型
     *
     * @param walletContentType
     * @return
     */
    Long insertWalletContentType(WalletContentType walletContentType);

    /**
     * 查询资讯类型列表
     *
     * @return
     */
    List<WalletContentTypeVo> queryTypeList();
}

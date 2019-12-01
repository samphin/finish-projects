package com.ryit.wallethelpserver.dao;

import com.ryit.commons.entity.pojo.WalletNotify;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/9 0009下午 3:49
 */
@Repository
public interface WalletNotifyMapper {

    List<WalletNotify> queryNotify(@Param("showFlag") Integer showFlag);

    int insertWalletNotify(WalletNotify walletNotify);

    int updateWalletNotify(WalletNotify walletNotify);

    int deleteWalletNotify(Long id);

    WalletNotify queryNotifyById(Long id);
}

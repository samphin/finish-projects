package com.ryit.wallethelpserver.dao;

import com.ryit.commons.entity.pojo.WalletHelp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006下午 1:38
 */
@Repository
public interface WalletHelpMapper {


    int insertWalletHelp(WalletHelp walletHelp);

    List<WalletHelp> queryWalletHelpList();

    WalletHelp queryWalletHelpById(Long id);

    int updateWalletHelp(WalletHelp walletHelp);

    /**
     * 批量删除问题
     *
     * @param ids
     * @return
     */
    int deleteWalletHelp(@Param("ids") List<Long> ids);

}

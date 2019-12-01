package com.ryit.walletmarketserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.WalletLoanMarketQueryDto;
import com.ryit.commons.entity.pojo.WalletLoanMarket;
import com.ryit.commons.entity.vo.WalletLoanMarketVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletmarketserver.dao.WalletLoanMarketMapper;
import com.ryit.walletmarketserver.service.WalletLoanMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005下午 2:39
 * @Description: 钱包贷款超市业务实现类
 */
@Service
public class WalletLoanMarketServiceImpl implements WalletLoanMarketService {

    private Logger log = LoggerFactory.getLogger(WalletLoanMarketServiceImpl.class);

    @Autowired
    private WalletLoanMarketMapper walletLoanMarketMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletLoanMarket(WalletLoanMarket walletLoanMarket) {
        try {
            walletLoanMarketMapper.insertWalletLoanMarket(walletLoanMarket);
            return walletLoanMarket.getId();
        } catch (Exception e) {
            log.error("后台添加钱包超市失败", e);
            throw new CustomException("后台添加钱包超市失败");
        }
    }

    @Override
    public List<WalletLoanMarketVo> queryWalletLoanMarketByCondition(WalletLoanMarketQueryDto dto) {
        try {
            // 0推荐 1下款快 2额度高 3期限长 4高通过 5利息低
            if (dto.getStatus() != null) {
                if (dto.getStatus().equals(0)) {
                    dto.setRecommend(1);
                }
                if (dto.getStatus().equals(1)) {
                    dto.setFastFlag(1);
                }
                if (dto.getStatus().equals(2)) {
                    dto.setHighFlag(1);
                }
                if (dto.getStatus().equals(3)) {
                    dto.setLongFlag(1);
                }
                if (dto.getStatus().equals(4)) {
                    dto.setPassFlag(1);
                }
                if (dto.getStatus().equals(5)) {
                    dto.setLowFlag(1);
                }
            }
            return walletLoanMarketMapper.queryWalletLoanMarketByCondition(dto);
        } catch (Exception e) {
            log.error("条件查询钱包超市列表失败", e);
            throw new CustomException("条件查询钱包超市列表失败");
        }
    }

    @Override
    public WalletLoanMarketVo queryWalletLoanMarketById(Long id) {
        try {
            return walletLoanMarketMapper.queryWalletLoanMarketById(id);
        } catch (Exception e) {
            log.error("查询钱包超市详情失败", e);
            throw new CustomException("查询钱包超市详情失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateWalletLoanMarket(WalletLoanMarket record) {
        try {
            return walletLoanMarketMapper.updateWalletLoanMarket(record);
        } catch (Exception e) {
            log.error("后台修改钱包超市失败", e);
            throw new CustomException("后台修改钱包超市失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWalletLoanMarket(String ids) {
        try {
            String[] strs = ids.split("\\,");
            List<String> strList = Arrays.asList(strs);
            List<Long> id = strList.stream().map(a -> Long.parseLong(a)).collect(Collectors.toList());
            return walletLoanMarketMapper.deleteWalletLoanMarket(id);
        } catch (Exception e) {
            log.error("后台批量删除钱包超市失败", e);
            throw new CustomException("后台批量删除钱包超市失败");
        }
    }

}

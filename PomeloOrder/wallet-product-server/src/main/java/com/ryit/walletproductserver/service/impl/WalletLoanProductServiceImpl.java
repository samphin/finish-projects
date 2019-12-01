package com.ryit.walletproductserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.WalletLoanProductDto;
import com.ryit.commons.entity.pojo.WalletLoanProduct;
import com.ryit.commons.entity.pojo.WalletTag;
import com.ryit.commons.entity.vo.WalletLoanProductVo;
import com.ryit.commons.util.DateUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletproductserver.dao.WalletLoanProductMapper;
import com.ryit.walletproductserver.dao.WalletTagMapper;
import com.ryit.walletproductserver.service.WalletLoanProductService;
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
 * @Date: 2019/9/5 0005上午 11:45
 * @Description：七一钱包贷款产品业务实现类
 */
@Service
public class WalletLoanProductServiceImpl implements WalletLoanProductService {

    @Autowired
    private WalletLoanProductMapper walletLoanProductMapper;

    @Autowired
    private WalletTagMapper walletTagMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(WalletLoanProductService.class);

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletLoanProduct(WalletLoanProduct record) {
        try {
            walletLoanProductMapper.insertWalletLoanProduct(record);
            return record.getId();
        } catch (Exception e) {
            LOGGER.error("添加钱包商品失败", e);
            throw new CustomException("添加钱包商品失败");
        }
    }

    @Override
    public List<WalletLoanProductVo> queryWalletLoanProductList(WalletLoanProductDto dto) {
        try {
            return walletLoanProductMapper.queryWalletLoanProductList(dto);
        } catch (Exception e) {
            LOGGER.error("查询钱包商品列表失败", e);
            throw new CustomException("查询钱包商品列表失败");
        }
    }

    @Override
    public WalletLoanProductVo queryWalletLoanProductById(Long id) {
        try {
            WalletLoanProductVo walletLoanProductVo = walletLoanProductMapper.queryWalletLoanProductById(id);
            List<WalletTag> tagList = walletTagMapper.queryByProductId(id);
            walletLoanProductVo.setList(tagList);
            return walletLoanProductVo;
        } catch (Exception e) {
            LOGGER.error("查询钱包商品详情失败", e);
            throw new CustomException("查询钱包商品详情失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateWalletLoanProduct(WalletLoanProduct record) {
        try {
            return walletLoanProductMapper.updateWalletLoanProduct(record);
        } catch (Exception e) {
            LOGGER.error("修改钱包商品详情失败", e);
            throw new CustomException("修改钱包商品详情失败");
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
            return walletLoanProductMapper.deleteWalletLoanProduct(id);
        } catch (Exception e) {
            LOGGER.error("批量删除钱包商品失败", e);
            throw new CustomException("批量删除钱包商品失败");
        }
    }

    @Override
    public List<WalletLoanProductVo> queryUserProduct(Long walletUserId) {
        try {
            List<WalletLoanProductVo> list = walletLoanProductMapper.queryUserProduct(walletUserId);
            list.forEach(vo -> vo.setTime(DateUtil.dateTime(vo.getReleaseTime())));
            return list;
        } catch (Exception e) {
            LOGGER.error("批量删除钱包商品失败", e);
            throw new CustomException("查询用户申请的贷款商品列表失败");
        }
    }

}

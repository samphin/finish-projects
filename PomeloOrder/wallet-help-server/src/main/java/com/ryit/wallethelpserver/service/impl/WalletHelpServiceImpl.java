package com.ryit.wallethelpserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.pojo.WalletHelp;
import com.ryit.commons.entity.vo.WalletHelpVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.wallethelpserver.dao.WalletHelpMapper;
import com.ryit.wallethelpserver.service.WalletHelpService;
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
 * @Date: 2019/9/6 0006下午 1:53
 */
@Service
public class WalletHelpServiceImpl implements WalletHelpService {

    private Logger log = LoggerFactory.getLogger(WalletHelpServiceImpl.class);

    @Autowired
    private WalletHelpMapper walletHelpMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer insertWalletHelp(WalletHelp walletHelp) {
        try {
            return walletHelpMapper.insertWalletHelp(walletHelp);
        } catch (Exception e) {
            log.error("后台添加钱包帮助失败", e);
            throw new CustomException("后台添加钱包帮助失败");
        }
    }

    @Override
    public WalletHelpVo queryWalletHelpList() {
        try {
            List<WalletHelp> list = walletHelpMapper.queryWalletHelpList();
            WalletHelpVo vo = new WalletHelpVo();
            vo.setList(list);
            return vo;
        } catch (Exception e) {
            log.error("查询钱包帮助列表失败", e);
            throw new CustomException("查询钱包帮助列表失败");
        }
    }

    @Override
    public List<WalletHelp> queryWalletHelps() {
        try {
            List<WalletHelp> list = walletHelpMapper.queryWalletHelpList();
            return list;
        } catch (Exception e) {
            log.error("后台查询钱包帮助列表失败", e);
            throw new CustomException("后台查询钱包帮助列表失败");
        }
    }

    @Override
    public WalletHelp queryWalletHelpById(Long id) {
        try {
            return walletHelpMapper.queryWalletHelpById(id);
        } catch (Exception e) {
            log.error("查询钱包帮助详情失败", e);
            throw new CustomException("查询钱包帮助详情失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWalletHelp(String ids) {
        try {
            String[] strs = ids.split("\\,");
            List<String> strList = Arrays.asList(strs);
            List<Long> id = strList.stream().map(a -> Long.parseLong(a)).collect(Collectors.toList());

            Integer num = walletHelpMapper.deleteWalletHelp(id);
            return num == id.size() ? true : false;
        } catch (Exception e) {
            log.error("后台批量删除钱包帮助失败", e);
            throw new CustomException("后台批量删除钱包帮助失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer updateWalletHelp(WalletHelp walletHelp) {
        try {
            return walletHelpMapper.updateWalletHelp(walletHelp);
        } catch (Exception e) {
            log.error("后台修改钱包帮助失败", e);
            throw new CustomException("后台修改钱包帮助失败");
        }
    }
}

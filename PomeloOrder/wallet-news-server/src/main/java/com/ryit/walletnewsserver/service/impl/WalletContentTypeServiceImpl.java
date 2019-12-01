package com.ryit.walletnewsserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.pojo.WalletContentType;
import com.ryit.commons.entity.vo.WalletContentTypeVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletnewsserver.dao.WalletContentTypeMapper;
import com.ryit.walletnewsserver.service.WalletContentTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 4:10
 */
@Service
public class WalletContentTypeServiceImpl implements WalletContentTypeService {

    private Logger log = LoggerFactory.getLogger(WalletContentTypeServiceImpl.class);

    @Autowired
    private WalletContentTypeMapper walletContentTypeMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long updateWalletContentType(WalletContentType walletContentType) {
        try {
            walletContentTypeMapper.updateWalletContentType(walletContentType);
            return walletContentType.getId();
        } catch (Exception e) {
            log.error("添加钱包资讯类型失败", e);
            throw new CustomException("添加钱包资讯类型失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletContentType(WalletContentType walletContentType) {
        try {
            walletContentTypeMapper.insertWalletContentType(walletContentType);
            return walletContentType.getId();
        } catch (Exception e) {
            log.error("修改钱包资讯类型失败", e);
            throw new CustomException("修改钱包资讯类型失败");
        }
    }

    @Override
    public List<WalletContentTypeVo> queryTypeList() {
        List<WalletContentTypeVo> list = walletContentTypeMapper.queryTypeList(null, null);
        List<WalletContentTypeVo> vos = new ArrayList<>();
        List<WalletContentTypeVo> voList = new ArrayList<>();
        list.stream().forEach(walletContentTypeVo -> {
            if (walletContentTypeVo.getLevel() == 1) {
                //顶级类型
                vos.add(walletContentTypeVo);
            } else {
                //非顶级类型
                voList.add(walletContentTypeVo);
            }
        });
        vos.stream().forEach(vo -> {
            List<WalletContentTypeVo> child = getChild(vo.getId(), voList);
            vo.setList(child);

        });
        return vos;
    }

    /**
     * 递归操作 对子级进行归类
     *
     * @param id
     * @param list
     * @return
     */
    public List<WalletContentTypeVo> getChild(Long id, List<WalletContentTypeVo> list) {
        List<WalletContentTypeVo> voList = new ArrayList<>();
        list.stream().forEach(vo -> {
            if (id.equals(vo.getParentId())) {
                vo.setList(getChild(vo.getId(), list));
                voList.add(vo);
            }
        });
        return voList;
    }
}

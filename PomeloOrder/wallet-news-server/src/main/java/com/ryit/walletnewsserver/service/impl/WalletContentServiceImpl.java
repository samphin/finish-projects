package com.ryit.walletnewsserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.pojo.WalletContent;
import com.ryit.commons.entity.vo.WalletContentTypeVo;
import com.ryit.commons.entity.vo.WalletContentVo;
import com.ryit.commons.util.RelativeDateFormat;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletnewsserver.dao.WalletContentMapper;
import com.ryit.walletnewsserver.dao.WalletContentTypeMapper;
import com.ryit.walletnewsserver.service.WalletContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/10 0010上午 10:24
 */
@Service
public class WalletContentServiceImpl implements WalletContentService {

    private Logger log = LoggerFactory.getLogger(WalletContentServiceImpl.class);

    @Autowired
    private WalletContentMapper walletContentMapper;

    @Autowired
    private WalletContentTypeMapper walletContentTypeMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletContent(WalletContent walletContent) {
        if (walletContent.getParentTypeId() == 3L && walletContent.getTypeId() == 3) {
            List<WalletContentVo> list = walletContentMapper.queryByNewsTitle(walletContent.getNewsTitle());
            if (!CollectionUtils.isEmpty(list)) {
                throw new CustomException("金融百科分类中该标题已存在");
            }
        }
        try {
            walletContent.setCreateTime(new Date());
            walletContentMapper.insertWalletContent(walletContent);
            return walletContent.getId();
        } catch (Exception e) {
            log.error("添加钱包分类资讯失败", e);
            throw new CustomException("添加钱包分类资讯失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long updateWalletContent(WalletContent walletContent) {
        try {
            walletContentMapper.updateWalletContent(walletContent);
            return walletContent.getId();
        } catch (Exception e) {
            log.error("修改钱包分类资讯失败", e);
            throw new CustomException("修改钱包分类资讯失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWalletContent(Long id) {
        try {
            return walletContentMapper.deleteWalletContent(id);
        } catch (Exception e) {
            log.error("删除钱包分类资讯失败", e);
            throw new CustomException("删除钱包分类资讯失败");
        }
    }

    @Override
    public List<WalletContentTypeVo> queryHomePageContent() {
        List<WalletContentTypeVo> list = walletContentTypeMapper.queryTypeList(null, 1);
        list.stream().forEach(vo -> {
            List<WalletContentVo> contents = walletContentMapper.queryContentListByParentId(vo.getId());
            contents.stream().forEach(c -> c.setDate(RelativeDateFormat.format(c.getCreateTime())));
            vo.setContentList(contents);
        });
        return list;
    }

    @Override
    public List<WalletContentTypeVo> queryContentByType(Long typeId) {
        //顶级分类id下的分类
        List<WalletContentTypeVo> list = walletContentTypeMapper.queryTypeByTopParentId(typeId);
        return getChild(typeId, list);
    }

    @Override
    public List<WalletContentVo> queryContentWithPage(Long typeId) {
        List<WalletContentVo> contentVos = walletContentMapper.queryContentsByTypeId(typeId);
        contentVos.stream().forEach(vo -> vo.setDate(RelativeDateFormat.format(vo.getCreateTime())));
        return contentVos;
    }

    @Override
    public List<WalletContentTypeVo> querySidebarContent(Long typeId) {
        List<WalletContentTypeVo> list = walletContentTypeMapper.querySidebarType(typeId);
        list.stream().forEach(vo -> {
            List<WalletContentVo> contentVos = walletContentMapper.querySimpleContent(vo.getId());
            vo.setContentList(contentVos);
        });
        return list;
    }

    @Override
    public WalletContentVo queryContentById(Long id) {
        WalletContentVo walletContentVo = walletContentMapper.queryById(id);
        walletContentVo.setDate(RelativeDateFormat.format(walletContentVo.getCreateTime()));
        return walletContentVo;
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
            List<WalletContentVo> contentVos = walletContentMapper.queryContentListByTypeId(vo.getId());
            vo.setContentList(contentVos);
            if (id.equals(vo.getParentId())) {
                vo.setList(getChild(vo.getId(), list));
                voList.add(vo);
            }
        });
        return voList;
    }
}

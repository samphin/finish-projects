package com.ryit.walletuserserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.pojo.WalletMenu;
import com.ryit.commons.entity.vo.WalletMenuVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletuserserver.dao.WalletMenuMapper;
import com.ryit.walletuserserver.service.WalletMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/21 0021下午 3:14
 */
@Service
public class WalletMenuServiceImpl implements WalletMenuService {

    private Logger log = LoggerFactory.getLogger(WalletMenuServiceImpl.class);

    @Autowired
    private WalletMenuMapper walletMenuMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer insertWalletMenu(WalletMenu walletMenu) {
        try {
            return walletMenuMapper.insertWalletMenu(walletMenu);
        } catch (Exception e) {
            log.error("添加后台钱包菜单失败", e);
            throw new CustomException("添加后台钱包菜单失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer updateWalletMenu(WalletMenu walletMenu) {
        try {
            return walletMenuMapper.updateWalletMenu(walletMenu);
        } catch (Exception e) {
            log.error("修改后台钱包菜单失败", e);
            throw new CustomException("修改后台钱包菜单失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWalletMenu(Long menuId) {
        try {
            return walletMenuMapper.deleteWalletMenu(menuId);
        } catch (Exception e) {
            log.error("删除后台钱包菜单失败", e);
            throw new CustomException("删除后台钱包菜单失败");
        }
    }

    @Override
    public List<WalletMenuVo> queryMenuList() {
        try {
            List<WalletMenuVo> list = walletMenuMapper.queryMenuList();
            List<WalletMenuVo> parentList = new ArrayList<>();
            List<WalletMenuVo> otherList = new ArrayList<>();
            list.forEach(vo -> {
                if (vo.getParentId() == 0) {
                    parentList.add(vo);
                } else {
                    otherList.add(vo);
                }
            });
            parentList.stream().forEach(vo -> {
                List<WalletMenuVo> child = getChild(otherList, vo.getMenuId());
                vo.setChild(child);

            });
            return parentList;
        } catch (Exception e) {
            log.error("查询后台菜单列表失败", e);
            throw new CustomException("查询后台菜单列表失败");
        }
    }

    @Override
    public WalletMenu queryMenuById(Long menuId) {
        return walletMenuMapper.queryMenuById(menuId);
    }

    @Override
    public List<WalletMenuVo> queryUserMenuList(Long userId) {
        try {
            List<WalletMenuVo> list = walletMenuMapper.queryUserMenuList(userId);
            List<WalletMenuVo> parentList = new ArrayList<>();
            List<WalletMenuVo> otherList = new ArrayList<>();
            list.forEach(vo -> {
                if (vo.getParentId() == 0) {
                    parentList.add(vo);
                } else {
                    otherList.add(vo);
                }
            });
            parentList.stream().forEach(vo -> {
                List<WalletMenuVo> child = getChild(otherList, vo.getMenuId());
                vo.setChild(child);
            });
            return parentList;
        } catch (Exception e) {
            log.error("查询后台菜单列表失败", e);
            throw new CustomException("查询后台菜单列表失败");
        }
    }

    /**
     * 递归查询子菜单
     *
     * @param list
     * @param menuId
     * @return
     */
    private List<WalletMenuVo> getChild(List<WalletMenuVo> list, Long menuId) {
        List<WalletMenuVo> voList = new ArrayList<>();
        list.stream().forEach(vo -> {
            if (menuId.equals(vo.getParentId())) {
                vo.setChild(getChild(list, vo.getMenuId()));
                voList.add(vo);
            }
        });
        return voList;
    }
}

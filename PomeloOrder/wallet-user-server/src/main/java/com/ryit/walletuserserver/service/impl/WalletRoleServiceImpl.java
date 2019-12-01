package com.ryit.walletuserserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.WalletRoleDto;
import com.ryit.commons.entity.pojo.WalletRole;
import com.ryit.commons.entity.vo.WalletMenuVo;
import com.ryit.commons.entity.vo.WalletRoleVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletuserserver.dao.WalletMenuMapper;
import com.ryit.walletuserserver.dao.WalletRoleMapper;
import com.ryit.walletuserserver.service.WalletRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/21 0021下午 3:00
 */
@Service
public class WalletRoleServiceImpl implements WalletRoleService {

    private Logger log = LoggerFactory.getLogger(WalletRoleServiceImpl.class);

    @Autowired
    private WalletRoleMapper walletRoleMapper;

    @Autowired
    private WalletMenuMapper walletMenuMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertWalletRole(WalletRoleDto walletRole) {
        List<Long> list = walletRoleMapper.queryByRoleNameOrRoleKey(walletRole.getRoleName(), walletRole.getRoleKey());
        if (!CollectionUtils.isEmpty(list)) {
            throw new CustomException("角色名称或角色字符串已存在");
        }
        try {
            //从dto中获取菜单id集合
            List<Long> menuIds = walletRole.getMenuIds();
            //获取role对象
            WalletRole role = new WalletRole();
            BeanUtils.copyProperties(walletRole, role);
            //保存新的role
            Integer num = walletRoleMapper.insertWalletRole(role);
            //添加菜单关联
            if (!CollectionUtils.isEmpty(menuIds)) {
                walletRoleMapper.insertRoleMenu(role.getRoleId(), menuIds);
            }
            return num > 0 ? true : false;
        } catch (Exception e) {
            log.error("后台添加钱包权限失败", e);
            throw new CustomException("后台添加钱包权限失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateWalletRole(WalletRoleDto walletRole) {
        List<Long> list = walletRoleMapper.queryByRoleNameOrRoleKey(walletRole.getRoleName(), walletRole.getRoleKey());
        if (!CollectionUtils.isEmpty(list) && list.size() > 1 && list.contains(walletRole.getRoleId())) {
            throw new CustomException("角色名称或角色字符串已存在");
        }
        try {
            //从dto中获取菜单id集合
            List<Long> menuIds = walletRole.getMenuIds();
            //获取role对象
            WalletRole role = new WalletRole();
            BeanUtils.copyProperties(walletRole, role);
            //保存修改后的role
            Integer num = walletRoleMapper.updateWalletRole(role);
            if (!CollectionUtils.isEmpty(menuIds)) {
                //删除之前关联的菜单
                walletRoleMapper.deleteAllRoleMenu(walletRole.getRoleId());
                //添加新的菜单
                walletRoleMapper.insertRoleMenu(role.getRoleId(), menuIds);
            }
            return num > 0 ? true : false;
        } catch (Exception e) {
            log.error("后台修改钱包权限失败", e);
            throw new CustomException("后台修改钱包权限失败");
        }
    }

    @Override
    public WalletRoleVo getRoleInfo(Long id) {
        try {
            List<Long> menuIds = walletMenuMapper.queryNotParentMenuId(id);
            WalletRoleVo vo = walletRoleMapper.getRoleInfo(id);
            vo.setMenuIds(menuIds);
            return vo;
        } catch (Exception e) {
            log.error("后台查询钱包权限详情失败", e);
            throw new CustomException("后台查询钱包权限详情失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRole(Long id) {
        try {
            Integer num = walletRoleMapper.deleteRole(id);
            return num > 0 ? true : false;
        } catch (Exception e) {
            log.error("后台删除钱包权限失败", e);
            throw new CustomException("后台删除钱包权限失败");
        }
    }

    @Override
    public List<WalletRoleVo> queryRoleByCondition(WalletRoleDto walletRoleDto) {
        try {
            return walletRoleMapper.queryRoleByCondition(walletRoleDto);
        } catch (Exception e) {
            log.error("后台条件查询钱包权限列表失败", e);
            throw new CustomException("后台条件查询钱包权限列表失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer insertRoleMenu(Long roleId, List<Long> list) {
        //查询差集 防止重复提交
        List<Long> menuIds = walletRoleMapper.queryMenuIdsByRole(roleId);
        list.removeAll(menuIds);
        if (CollectionUtils.isEmpty(list)) {
            throw new CustomException("选中的菜单在该角色都已添加");
        }
        try {
            return walletRoleMapper.insertRoleMenu(roleId, list);
        } catch (Exception e) {
            log.error("后台添加钱包菜单权限失败", e);
            throw new CustomException("后台添加钱包菜单权限失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteRoleMenu(Long roleId, List<Long> list) {
        try {
            //查询并集
            List<Long> menuIds = walletRoleMapper.queryMenuIdsByRole(roleId);
            list.retainAll(menuIds);
            return walletRoleMapper.deleteRoleMenu(roleId, list);
        } catch (Exception e) {
            log.error("后台删除钱包菜单权限失败", e);
            throw new CustomException("后台删除钱包菜单权限失败");
        }
    }

    @Override
    public List<WalletMenuVo> queryRoleMenu(Long roleId) {
        try {
            List<WalletMenuVo> list = walletMenuMapper.queryRoleMenu(roleId);
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
            log.error("查询后台权限菜单列表失败", e);
            throw new CustomException("查询后台权限菜单列表失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer insertUserRole(Long userId, Long roleId) {
        try {
            //先删除之前的角色
            walletRoleMapper.deleteAllUserRole(userId);
            return walletRoleMapper.insertUserRole(userId, roleId);
        } catch (Exception e) {
            log.error("添加钱包后台用户权限失败", e);
            throw new CustomException("添加钱包后台用户权限失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteUserRole(Long userId, List<Long> list) {
        try {
            //查询并集
            List<Long> roleIds = walletRoleMapper.queryRoleIdsByUser(userId);
            list.retainAll(roleIds);
            return walletRoleMapper.deleteUserRole(userId, list);
        } catch (Exception e) {
            log.error("删除后台用户权限失败", e);
            throw new CustomException("删除后台用户权限失败");
        }
    }

    @Override
    public List<WalletRoleVo> queryUserRole(Long userId) {
        try {
            return walletRoleMapper.queryUserRole(userId);
        } catch (Exception e) {
            log.error("查询后台用户权限列表失败", e);
            throw new CustomException("查询后台用户权限列表失败");
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

package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.SysMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu> {

    /**
     * 分页查询
     *
     * @param po
     * @return
     */
    List<SysMenu> selectList(SysMenu po);

    /**
     * 获取菜单按钮
     *
     * @return
     */
    List<SysMenu> selectButtonsByMenuId(@Param("menuId") Integer menuId);

    /**
     * 查询用户拥有菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> selectUserHaveMenus(@Param("userId") Integer userId);
}
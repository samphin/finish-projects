package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.SysMenuDto;
import com.ryit.commons.entity.vo.SysMenuListVo;
import com.ryit.commons.entity.vo.SysMenuVo;
import com.ryit.commons.entity.vo.SysUserMenuVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISysMenuService extends IBaseService<Integer, SysMenuDto, SysMenuVo> {

    /**
     * 查询菜单拥有的按钮信息
     *
     * @param id
     * @return
     */
    List<SysMenuListVo> queryMenuButtons(Integer id);

    /**
     * 查询用户拥有菜单、按钮信息
     *
     * @param request
     * @return
     */
    SysUserMenuVo queryUserHaveMenus(HttpServletRequest request);
}

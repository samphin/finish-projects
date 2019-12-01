package com.ryit.users.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.SysMenuDto;
import com.ryit.commons.entity.pojo.SysMenu;
import com.ryit.commons.entity.vo.SysMenuListVo;
import com.ryit.commons.entity.vo.SysMenuVo;
import com.ryit.commons.entity.vo.SysUserMenuVo;
import com.ryit.users.mapper.SysMenuMapper;
import com.ryit.users.service.ISysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends BaseServiceImpl<Integer, SysMenuDto, SysMenuVo> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(SysMenuDto dto) {
        SysMenu po = new SysMenu().buildPo(dto);
        return this.sysMenuMapper.insertSelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(SysMenuDto dto) {
        SysMenu po = new SysMenu().buildPo(dto);
        return this.sysMenuMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer id) {
        return this.sysMenuMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public PageBean<SysMenuVo> queryPageList(BaseQueryDto<SysMenuDto> queryDto) {

        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());

        SysMenuDto dto = queryDto.getParam();

        SysMenu po = new SysMenu().buildPo(dto);

        List<SysMenu> poList = this.sysMenuMapper.selectList(po);

        List<SysMenuVo> voList = new SysMenuVo().buildVoList(poList);

        return getPageData(voList, page);
    }

    @Override
    public SysMenuVo queryById(Integer id) {

        SysMenu po = this.sysMenuMapper.selectByPrimaryKey(id);

        SysMenuVo vo = new SysMenuVo().buildVo(po);

        return vo;

    }

    /**
     * 查询用户拥有菜单、按钮信息
     *
     * @param request
     * @return
     */
    @Override
    public SysUserMenuVo queryUserHaveMenus(HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        SysUserMenuVo vo = new SysUserMenuVo();
        //查询用户拥有的菜单与按钮信息
        List<SysMenu> poList = this.sysMenuMapper.selectUserHaveMenus(userId);
        //筛选出菜单
        List<SysMenu> menus = poList.stream().filter(sysMenu -> "Menu".equals(sysMenu.getType())).collect(Collectors.toList());
        //筛选出按钮
        List<SysMenu> buttons = poList.stream().filter(sysMenu -> "Button".equals(sysMenu.getType())).collect(Collectors.toList());
        //根据菜单ID进行分组
        Map<Integer, List<SysMenu>> menuButtons = buttons.stream().collect(Collectors.groupingBy(SysMenu::getParentId));
        JSONObject buttonData = new JSONObject();
        menuButtons.keySet().forEach(menuId -> {
            //获取当前菜单下面所有按钮
            Optional<String> optional = menus.stream().filter(menu -> menu.getId().equals(menuId)).findFirst().map(SysMenu::getName);
            optional.ifPresent(menuName->{
                List<SysMenu> belongButtons = menuButtons.get(menuId);
                buttonData.put(menuName, belongButtons);
            });
        });
        List<SysMenuVo> voList = new SysMenuVo().buildVoList(menus);
        //将菜单列表，格式化成树型结构显示
        List<SysMenuVo> menuVoList = queryOneLevelMenu(voList);

        vo.setMenus(menuVoList);
        vo.setButtons(buttonData);
        return vo;
    }

    @Override
    public List<SysMenuVo> queryList() {

        List<SysMenu> poList = this.sysMenuMapper.selectList(null);

        List<SysMenuVo> voList = new SysMenuVo().buildVoList(poList);

        //将菜单列表，格式化成树型结构显示
        List<SysMenuVo> menuVoList = queryOneLevelMenu(voList);

        return menuVoList;
    }

    /**
     * 查询菜单选择框数据
     * @param dto
     * @return
     */
    @Override
    public List<SysMenuVo> queryListByCondition(SysMenuDto dto) {
        SysMenu po = new SysMenu();
        po.setType("Menu");
        List<SysMenu> poList = this.sysMenuMapper.selectList(po);

        List<SysMenuVo> voList = new SysMenuVo().buildVoList(poList);

        return voList;
    }

    @Override
    public List<SysMenuListVo> queryMenuButtons(Integer id) {

        List<SysMenu> buttonPoList = sysMenuMapper.selectButtonsByMenuId(id);

        List<SysMenuListVo> buttonVoList = new SysMenuListVo().buildVoList(buttonPoList);

        return buttonVoList;
    }

    /**
     * 获取所有一级菜单
     *
     * @return
     */
    private List<SysMenuVo> queryOneLevelMenu(List<SysMenuVo> sysMenuVos) {
        List<SysMenuVo> oneLevelMenus = new ArrayList<>();
        for (SysMenuVo oneVO : sysMenuVos) {
            //过滤出所有一级菜单
            if (oneVO.getParentId() == 0 && oneVO.getLevel() == 1) {
                oneLevelMenus.add(oneVO);
            }
        }

        //非一级菜单
        List<SysMenuVo> noOneLevelMenus = queryNoOneLevelMenu(sysMenuVos);

        //循环一级菜单，将所有一级菜单对应的子菜单都循环遍历
        for (SysMenuVo oneVo : oneLevelMenus) {
            oneVo.setChildren(queryChildren(oneVo.getId(), noOneLevelMenus));
        }
        return oneLevelMenus;
    }

    /**
     * 过滤出所有非一级菜单
     *
     * @param sysMenuVos
     * @return
     */
    private List<SysMenuVo> queryNoOneLevelMenu(List<SysMenuVo> sysMenuVos) {
        Iterator<SysMenuVo> it = sysMenuVos.iterator();
        while (it.hasNext()) {
            SysMenuVo vo = it.next();
            if (vo.getParentId() == 0 && vo.getLevel() == 1) {
                it.remove();
            }
        }
        return sysMenuVos;
    }

    /**
     * 递归查询所有子菜单
     *
     * @param parentId
     * @param noOneLevelMenus
     * @return
     */
    private List<SysMenuVo> queryChildren(Integer parentId, List<SysMenuVo> noOneLevelMenus) {
        List<SysMenuVo> menuList = new ArrayList<>();
        for (SysMenuVo menuVO : noOneLevelMenus) {
            //如果当前菜单ID与子菜单的parentId相同，则将这些子菜单汇总到上级菜单的children属性中
            if (parentId.equals(menuVO.getParentId())) {
                if (menuVO.getLeaf() == false) {
                    menuVO.setChildren(queryChildren(menuVO.getId(), noOneLevelMenus));
                }
                menuList.add(menuVO);
            }
        }
        return menuList;
    }
}

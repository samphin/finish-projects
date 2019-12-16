package com.ryit.users.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.SysRoleDto;
import com.ryit.commons.entity.dto.SysRolePermissionPkDto;
import com.ryit.commons.entity.dto.SysRolePermissionPkQueryDto;
import com.ryit.commons.entity.pojo.SysPermission;
import com.ryit.commons.entity.pojo.SysRole;
import com.ryit.commons.entity.pojo.SysRolePermissionPk;
import com.ryit.commons.entity.vo.SysPermissionVo;
import com.ryit.commons.entity.vo.SysRoleVo;
import com.ryit.users.mapper.SysPermissionMapper;
import com.ryit.users.mapper.SysRoleMapper;
import com.ryit.users.mapper.SysRolePermissionPkMapper;
import com.ryit.users.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SysRoleServiceImpl extends BaseServiceImpl<Integer, SysRoleDto, SysRoleVo> implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRolePermissionPkMapper sysRolePermissionPkMapper;

    @Resource
    private SysPermissionMapper permissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(SysRoleDto dto) {
        SysRole po = new SysRole().buildPo(dto);
        return sysRoleMapper.insertSelective(po) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(SysRoleDto dto) {
        SysRole po = new SysRole().buildPo(dto);
        return sysRoleMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer id) {
        return sysRoleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public PageBean<SysRoleVo> queryPageList(BaseQueryDto<SysRoleDto> queryDto) {

        //开启分页
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());

        //获取查询参数
        SysRoleDto dto = queryDto.getParam();

        //组装查询条件
        SysRole po = new SysRole().buildPo(dto);

        //返回查询结果
        List<SysRole> poList = this.sysRoleMapper.selectList(po);

        //将poList转换成voList
        List<SysRoleVo> voList = SysRoleVo.buildSysRoleList(poList);

        return getPageData(voList, page);
    }

    @Override
    public SysRoleVo queryById(Integer id) {

        SysRole po = this.sysRoleMapper.selectByPrimaryKey(id);

        SysRoleVo vo = SysRoleVo.buildSysRole(po);

        return vo;
    }

    @Override
    public List<SysRoleVo> queryListByCondition(SysRoleDto dto) {

        SysRole po = new SysRole().buildPo(dto);

        //返回查询结果
        List<SysRole> poList = this.sysRoleMapper.selectList(po);

        //将poList转换成voList
        List<SysRoleVo> voList = SysRoleVo.buildSysRoleList(poList);

        return voList;
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<SysRoleVo> queryList() {
        //返回查询结果
        List<SysRole> poList = this.sysRoleMapper.selectAll();
        //将poList转换成voList
        List<SysRoleVo> voList = SysRoleVo.buildSysRoleList(poList);
        return voList;
    }


    /***************************给角色绑定权限*******************/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindingPermissions(SysRolePermissionPkDto dto) {
        //先清空当前角色旧数据
        SysRolePermissionPk sysRolePermissionPk = new SysRolePermissionPk();
        sysRolePermissionPk.setRoleId(dto.getRoleId());
        sysRolePermissionPkMapper.delete(sysRolePermissionPk);

        //绑定新的权限信息
        List<SysRolePermissionPk> poList = new SysRolePermissionPk().buildPoList(dto);
        return sysRolePermissionPkMapper.saveBatch(poList) > 0;
    }

    @Override
    public List<SysPermissionVo> queryRoleHavePermissions(Integer roleId) {

        List<SysPermission> poList = this.permissionMapper.selectRoleHavePermissions(roleId);

        List<SysPermissionVo> voList = new SysPermissionVo().buildVoList(poList);

        return voList;
    }

    @Override
    public List<SysPermissionVo> queryRoleHaveNoPermissions(SysRolePermissionPkQueryDto dto) {

        List<SysPermission> poList = this.permissionMapper.selectRoleHaveNoPermissions(dto.getRoleId(), dto.getName());

        List<SysPermissionVo> voList = new SysPermissionVo().buildVoList(poList);

        return voList;
    }

}

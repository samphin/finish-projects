package com.ryit.users.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.SysPermissionDto;
import com.ryit.commons.entity.pojo.SysPermission;
import com.ryit.commons.entity.pojo.SysPermissionResourcePk;
import com.ryit.commons.entity.vo.SysPermissionVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.users.mapper.SysPermissionMapper;
import com.ryit.users.mapper.SysPermissionResourcePkMapper;
import com.ryit.users.service.ISysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<Integer, SysPermissionDto, SysPermissionVo> implements ISysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private SysPermissionResourcePkMapper sysPermissionResourcePkMapper;

    @Override
    public PageBean<SysPermissionVo> queryPageList(BaseQueryDto<SysPermissionDto> queryDto) {

        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());

        SysPermissionDto dto = queryDto.getParam();

        SysPermission po = new SysPermission().buildPo(dto);

        List<SysPermission> poList = this.sysPermissionMapper.selectList(po);

        List<SysPermissionVo> voList = new SysPermissionVo().buildVoList(poList);

        return getPageData(voList, page);
    }

    @Override
    public List<SysPermissionVo> queryListByCondition(SysPermissionDto dto) {

        SysPermission po = new SysPermission().buildPo(dto);

        List<SysPermission> poList = this.sysPermissionMapper.selectList(po);

        List<SysPermissionVo> voList = new SysPermissionVo().buildVoList(poList);

        return voList;
    }

    @Override
    public SysPermissionVo queryById(Integer id) {

        SysPermission po = this.sysPermissionMapper.selectByPrimaryKey(id);

        SysPermissionVo vo = new SysPermissionVo().buildVo(po);

        List<SysPermissionResourcePk> poList = sysPermissionResourcePkMapper.selectPermissionHaveResource(id);
        if (!CollectionUtils.isEmpty(poList)) {
            List<Integer> resourceIds = poList.stream().map(SysPermissionResourcePk::getResourceId).collect(Collectors.toList());
            vo.setResourceIds(resourceIds);
        }

        return vo;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateState(SysPermissionDto dto) {
        SysPermission po = new SysPermission().buildPo(dto);
        return this.sysPermissionMapper.updateByPrimaryKeySelective(po) > 0;
    }

    /**
     * 保存权限信息
     *
     * @param dto
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(SysPermissionDto dto) {
        try {
            SysPermission po = new SysPermission().buildPo(dto);
            if (null != dto.getId()) {
                this.sysPermissionMapper.updateByPrimaryKeySelective(po);
            } else {
                this.sysPermissionMapper.insertSelective(po);
            }

            //当前权限ID
            Integer permissionId = po.getId();
            //保存叶子节点
            List<Integer> parentResourceIds = dto.getParentResourceIds();
            List<SysPermissionResourcePk> allPermissions = new ArrayList<>();
            if (!CollectionUtils.isEmpty(parentResourceIds)) {
                //保存权限最新绑定的资源信息
                List<SysPermissionResourcePk> poList = new SysPermissionResourcePk().buildPoList(permissionId, parentResourceIds, 1);
                allPermissions.addAll(poList);
            }
            //权限非叶子节点的资源信息
            List<Integer> resourceIds = dto.getResourceIds();
            if (!CollectionUtils.isEmpty(resourceIds)) {
                //保存权限最新绑定的资源信息
                List<SysPermissionResourcePk> poList = new SysPermissionResourcePk().buildPoList(permissionId, resourceIds, 0);
                allPermissions.addAll(poList);
            }

            //先删除旧权限信息
            Example example = new Example(SysPermissionResourcePk.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("permissionId", permissionId);
            this.sysPermissionResourcePkMapper.deleteByExample(example);

            this.sysPermissionResourcePkMapper.insertBatch(allPermissions);
            return true;
        } catch (Exception e) {
            throw new CustomException("权限保存失败", e);
        }
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer id) {
        return sysPermissionMapper.deleteByPrimaryKey(id) > 0;
    }


    /*************************权限分配资源***********************************/
    @Override
    public List<Integer> queryPermissionHaveResource(Integer permissionId) {

        List<SysPermissionResourcePk> poList = sysPermissionResourcePkMapper.selectPermissionHaveResource(permissionId);

        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream().map(SysPermissionResourcePk::getResourceId).collect(Collectors.toList());
    }
}

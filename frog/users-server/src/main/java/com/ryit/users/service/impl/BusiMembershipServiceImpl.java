package com.ryit.users.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiMembershipDto;
import com.ryit.commons.entity.pojo.BusiMembership;
import com.ryit.commons.entity.vo.BusiMemberOrderVo;
import com.ryit.commons.entity.vo.BusiMembershipVo;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.users.mapper.BusiMembershipMapper;
import com.ryit.users.service.IBusiMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class BusiMembershipServiceImpl extends BaseServiceImpl<Long, BusiMembershipDto, BusiMembershipVo> implements IBusiMembershipService {

    @Autowired
    private BusiMembershipMapper busiMembershipMapper;

    @Override
    public boolean insertSelective(BusiMembershipDto dto, HttpServletRequest request) {
        int userId = getCurrentUserId(request);
        BusiMembership po = new BusiMembership().buildPo(dto);
        po.setId(SnowflakeIdWorker.generateId());
        po.setUserId(userId);
        po.setCreateUserId(userId);
        po.setCreateDate(new Date());
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        return busiMembershipMapper.insertSelective(po) > 0;
    }

    @Override
    public boolean updateByIdSelective(BusiMembershipDto dto, HttpServletRequest request) {
        int userId = getCurrentUserId(request);
        BusiMembership po = new BusiMembership().buildPo(dto);
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        return busiMembershipMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return busiMembershipMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public PageBean<BusiMembershipVo> queryPageList(BaseQueryDto<BusiMembershipDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiMembershipDto dto = queryDto.getParam();
        List<BusiMembershipVo> voList = busiMembershipMapper.selectMemberList(dto);
        return getPageData(voList, page);
    }

    /**
     * 查询会员数量
     *
     * @param dto
     * @return
     */
    @Override
    public int queryCount(BusiMembershipDto dto) {
        int count = busiMembershipMapper.selectCount(null);
        return count;
    }

    @Override
    public BusiMembershipVo queryMemberDetail(Integer userId) {
        BusiMembershipVo vo = busiMembershipMapper.selectMemberDetail(userId);
        return vo;
    }

    @Override
    public List<BusiMemberOrderVo> queryMemberOrderNum() {
        return busiMembershipMapper.selectMemberOrderNum();
    }
}
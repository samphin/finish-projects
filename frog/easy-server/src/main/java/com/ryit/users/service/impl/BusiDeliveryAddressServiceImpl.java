package com.ryit.users.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiDeliveryAddressDto;
import com.ryit.commons.entity.pojo.BusiDeliveryAddress;
import com.ryit.commons.entity.vo.BusiDeliveryAddressVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.users.mapper.BusiDeliveryAddressMapper;
import com.ryit.users.service.IBusiDeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 收货地址Service
 *
 * @author samphin
 * @since 2019-10-28 11:13:30
 */
@Service
public class BusiDeliveryAddressServiceImpl extends BaseServiceImpl<Long, BusiDeliveryAddressDto, BusiDeliveryAddressVo> implements IBusiDeliveryAddressService {

    @Autowired
    private BusiDeliveryAddressMapper busiDeliveryAddressMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(BusiDeliveryAddressDto dto, HttpServletRequest request) {
        try {
            int userId = getCurrentUserId(request);
            BusiDeliveryAddress po = new BusiDeliveryAddress().buildPo(dto);
            po.setId(SnowflakeIdWorker.generateId());
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());
            //如果新增地址是默认地址，则将旧地址还原成非默认
            if (1 == dto.getDefaultStatus()) {
                busiDeliveryAddressMapper.resetOtherAddress(userId, null);
            }
            busiDeliveryAddressMapper.insertSelective(po);
            return true;
        } catch (CustomException e) {
            throw new CustomException("收货地址新增失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(BusiDeliveryAddressDto dto, HttpServletRequest request) {
        try {
            int userId = getCurrentUserId(request);
            BusiDeliveryAddress po = new BusiDeliveryAddress().buildPo(dto);
            po.setLastUpdateUserId(userId);
            po.setLastUpdateDate(new Date());
            busiDeliveryAddressMapper.updateByPrimaryKeySelective(po);
            //如果修改的地址是默认地址，则将旧地址还原成非默认
            if (1 == dto.getDefaultStatus()) {
                busiDeliveryAddressMapper.resetOtherAddress(userId, po.getId());
            }
            return true;
        } catch (CustomException e) {
            throw new CustomException("收货地址修改失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return busiDeliveryAddressMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<BusiDeliveryAddressVo> queryList() {
        List<BusiDeliveryAddress> poList = busiDeliveryAddressMapper.selectAll();
        return new BusiDeliveryAddressVo().buildVoList(poList);
    }

    @Override
    public PageBean<BusiDeliveryAddressVo> queryPageList(BaseQueryDto<BusiDeliveryAddressDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        List<BusiDeliveryAddressVo> voList = queryList();
        return getPageData(voList, page);
    }

    @Override
    public BusiDeliveryAddressVo queryById(Long id) {
        BusiDeliveryAddress po = busiDeliveryAddressMapper.selectByPrimaryKey(id);
        return new BusiDeliveryAddressVo().buildVo(po);
    }

    @Override
    public List<BusiDeliveryAddressVo> queryMyAddress(HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        Example example = new Example(BusiDeliveryAddress.class);
        //按照默认地址倒序
        example.orderBy("defaultStatus").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("createUserId", userId);
        List<BusiDeliveryAddress> poList = busiDeliveryAddressMapper.selectByExample(example);
        List<BusiDeliveryAddressVo> voList = new BusiDeliveryAddressVo().buildVoList(poList);
        return voList;
    }
}

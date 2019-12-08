package com.ryit.goods.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsBrandDto;
import com.ryit.commons.entity.pojo.BusiGoodsBrand;
import com.ryit.commons.entity.vo.BusiGoodsBrandVo;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.goods.mapper.BusiGoodsBrandMapper;
import com.ryit.goods.service.IBusiGoodsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class BusiGoodsBrandServiceImpl extends BaseServiceImpl<Long, BusiGoodsBrandDto, BusiGoodsBrandVo> implements IBusiGoodsBrandService {

    @Autowired
    private BusiGoodsBrandMapper busiGoodsBrandMapper;

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(BusiGoodsBrandDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiGoodsBrand po = new BusiGoodsBrand().buildPo(dto);
        po.setId(SnowflakeIdWorker.generateId());
        po.setCreateUserId(userId);
        po.setCreateDate(new Date());
        return busiGoodsBrandMapper.insert(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(BusiGoodsBrandDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiGoodsBrand po = new BusiGoodsBrand().buildPo(dto);
        po.setId(SnowflakeIdWorker.generateId());
        po.setCreateUserId(userId);
        po.setLastUpdateUserId(userId);
        return busiGoodsBrandMapper.insertSelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(BusiGoodsBrandDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiGoodsBrand po = new BusiGoodsBrand().buildPo(dto);
        po.setLastUpdateUserId(userId);
        return busiGoodsBrandMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return busiGoodsBrandMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        Iterator iterator = ids.iterator();
        Example example = new Example(BusiGoodsBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", (Iterable) iterator);
        return busiGoodsBrandMapper.deleteByExample(example) > 0;
    }

    @Override
    public List<BusiGoodsBrandVo> queryListByCondition(BusiGoodsBrandDto dto) {
        Example example = new Example(BusiGoodsBrand.class);
        example.orderBy("sort").asc();
        List<BusiGoodsBrand> poList = this.busiGoodsBrandMapper.selectByExample(example);
        return new BusiGoodsBrandVo().buildVoList(poList);
    }

    /**
     * 分页查询
     *
     * @param queryDto
     * @return
     */
    @Override
    public PageBean<BusiGoodsBrandVo> queryPageList(BaseQueryDto<BusiGoodsBrandDto> queryDto) {

        Page<BusiGoodsBrand> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);

        BusiGoodsBrandDto queryParams = queryDto.getParam();

        List<BusiGoodsBrandVo> voList = queryListByCondition(queryParams);

        return getPageData(voList, page);
    }

    @Override
    public BusiGoodsBrandVo queryById(Long id) {
        BusiGoodsBrand po = busiGoodsBrandMapper.selectByPrimaryKey(id);
        BusiGoodsBrandVo vo = new BusiGoodsBrandVo().buildVo(po);
        return vo;
    }
}
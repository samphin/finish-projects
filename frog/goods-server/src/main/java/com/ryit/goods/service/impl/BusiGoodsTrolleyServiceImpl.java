package com.ryit.goods.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyDto;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyUpdateDto;
import com.ryit.commons.entity.pojo.BusiGoodsTrolley;
import com.ryit.commons.entity.vo.BusiGoodsTrolleyVo;
import com.ryit.goods.mapper.BusiGoodsSkuMapper;
import com.ryit.goods.mapper.BusiGoodsTrolleyMapper;
import com.ryit.goods.service.IBusiGoodsTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 购物车业务类
 *
 * @author samphin
 * @since 2019-10-24 11:39:47
 */
@Service
public class BusiGoodsTrolleyServiceImpl extends BaseServiceImpl<Long, BusiGoodsTrolleyDto, BusiGoodsTrolleyVo> implements IBusiGoodsTrolleyService {

    /**
     * 商品规格
     */
    @Autowired
    private BusiGoodsSkuMapper busiGoodsSkuMapper;

    /**
     * 购物车
     */
    @Autowired
    private BusiGoodsTrolleyMapper busiGoodsTrolleyMapper;

    /**
     * 加入购物车
     *
     * @param trolleyDtos
     * @author samphin
     * @since 2019-11-18 10:29:07
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean addTrolley(List<BusiGoodsTrolleyDto> trolleyDtos, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);

        //将对应规格商品加入购物车
        List<BusiGoodsTrolley> poList = new BusiGoodsTrolley().buildPoList(trolleyDtos, userId);

        return busiGoodsTrolleyMapper.insertBatch(poList) > 0;
    }

    /**
     * 修改购物车商品规格数量
     *
     * @param dto
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTrolleyNum(BusiGoodsTrolleyUpdateDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        BusiGoodsTrolley po = new BusiGoodsTrolley();
        po.setId(dto.getId());
        po.setAmount(dto.getAmount());
        po.setTotalPrice(dto.getTotalPrice());
        //单价设置【公式：总价/数量】
        //总价
        BigDecimal totalPrice = BigDecimal.valueOf(dto.getTotalPrice());
        //数量
        BigDecimal amount = BigDecimal.valueOf(dto.getAmount());
        //单价
        BigDecimal price = totalPrice.divide(amount, BigDecimal.ROUND_CEILING);
        po.setPrice(price.doubleValue());
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        return busiGoodsTrolleyMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    public List<BusiGoodsTrolleyVo> queryMyTrolley(HttpServletRequest request) {
        Integer currentUserId = getCurrentUserId(request);
        List<BusiGoodsTrolley> poList = busiGoodsTrolleyMapper.selectMyTrolley(currentUserId);
        List<BusiGoodsTrolleyVo> voList = new BusiGoodsTrolleyVo().buildVoList(poList);
        return voList;
    }

    /**
     * 删除购物车商品
     *
     * @param idList
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> idList) {
        return busiGoodsTrolleyMapper.deleteBatch(idList) > 0;
    }

    /**
     * 清空购购物车
     *
     * @return
     * @author samphin
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean empty(HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        Example example = new Example(BusiGoodsTrolley.class);
        example.createCriteria().andEqualTo("createUserId", userId);
        return busiGoodsTrolleyMapper.deleteByExample(example) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTrolleyGoods(Set<Long> goodsIdList, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        Example example = new Example(BusiGoodsTrolley.class);
        example.createCriteria().andEqualTo("createUserId", userId).andIn("goodsId", goodsIdList);
        return busiGoodsTrolleyMapper.deleteByExample(example) > 0;
    }

    @Override
    public PageBean<BusiGoodsTrolleyVo> queryPageList(BaseQueryDto<BusiGoodsTrolleyDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiGoodsTrolleyDto dto = queryDto.getParam();
        BusiGoodsTrolley po = new BusiGoodsTrolley().buildPo(dto);
        List<BusiGoodsTrolley> poList = busiGoodsTrolleyMapper.select(po);
        List<BusiGoodsTrolleyVo> voList = new BusiGoodsTrolleyVo().buildVoList(poList);
        return getPageData(voList, page);
    }
}
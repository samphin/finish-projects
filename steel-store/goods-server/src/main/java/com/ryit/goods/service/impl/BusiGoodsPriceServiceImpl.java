package com.ryit.goods.service.impl;


import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsPriceDto;
import com.ryit.commons.entity.dto.BusiGoodsPriceQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsRolledPriceDto;
import com.ryit.commons.entity.dto.BusiGoodsRolledPriceQueryDto;
import com.ryit.commons.entity.pojo.BusiGoods;
import com.ryit.commons.entity.pojo.BusiGoodsPrice;
import com.ryit.commons.entity.pojo.BusiGoodsRolledPrice;
import com.ryit.commons.entity.vo.BusiGoodsPriceListVo;
import com.ryit.commons.entity.vo.BusiGoodsPriceVo;
import com.ryit.commons.entity.vo.BusiGoodsRolledPriceListVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.goods.mapper.BusiGoodsMapper;
import com.ryit.goods.mapper.BusiGoodsPriceMapper;
import com.ryit.goods.mapper.BusiGoodsRolledPriceMapper;
import com.ryit.goods.service.IBusiGoodsPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class BusiGoodsPriceServiceImpl extends BaseServiceImpl<Long, BusiGoodsPriceDto, BusiGoodsPriceVo> implements IBusiGoodsPriceService {

    @Autowired
    private BusiGoodsPriceMapper busiGoodsPriceMapper;

    @Autowired
    private BusiGoodsRolledPriceMapper busiGoodsRolledPriceMapper;

    @Autowired
    private BusiGoodsMapper busiGoodsMapper;

    /**
     * 设置商品今日价格
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(BusiGoodsPriceDto dto, HttpServletRequest request) {
        try {
            //获取当前用户ID
            int userId = getCurrentUserId(request);
            BusiGoodsPrice po = new BusiGoodsPrice().buildPo(dto);
            po.setId(SnowflakeIdWorker.generateId());
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());
            //新增商品当日价格
            busiGoodsPriceMapper.insertSelective(po);

            //设置最新价格到商品的市场价字段中
            BusiGoods goodsPo = new BusiGoods();
            goodsPo.setId(dto.getGoodsId());
            goodsPo.setMarketPrice(Double.valueOf(dto.getPrice()));
            busiGoodsMapper.updateByPrimaryKeySelective(goodsPo);
            return true;
        } catch (CustomException e) {
            throw new CustomException(SystemErrorEnum.GOODS_PRICE_SET_ERROR);
        }
    }

    @Override
    public PageBean<BusiGoodsPriceListVo> queryGoodPriceList(BaseQueryDto<BusiGoodsPriceQueryDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiGoodsPriceQueryDto param = queryDto.getParam();
        List<BusiGoodsPriceListVo> voList = busiGoodsPriceMapper.selectGoodPriceList(param);
        return getPageData(voList, page);
    }

    @Override
    public boolean saveScrapIronPrice(BusiGoodsRolledPriceDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        BusiGoodsRolledPrice po = new BusiGoodsRolledPrice();
        po.setId(SnowflakeIdWorker.generateId());
        po.setPrice(dto.getPrice());
        po.setCreateUserId(userId);
        po.setCreateDate(new Date());
        return busiGoodsRolledPriceMapper.insertSelective(po) > 0;
    }

    @Override
    public PageBean<BusiGoodsRolledPriceListVo> queryScrapIronPriceList(BaseQueryDto<BusiGoodsRolledPriceQueryDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiGoodsRolledPriceQueryDto dto = queryDto.getParam();
        List<BusiGoodsRolledPriceListVo> voList = busiGoodsRolledPriceMapper.selectList(dto);
        return getPageData(voList, page);
    }
}

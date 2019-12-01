package com.ryit.goods.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsSkuDto;
import com.ryit.commons.entity.pojo.BusiGoodsSku;
import com.ryit.commons.entity.vo.BusiGoodsSkuInfoVo;
import com.ryit.commons.entity.vo.BusiGoodsSkuVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.goods.mapper.BusiGoodsPriceMapper;
import com.ryit.goods.mapper.BusiGoodsSkuMapper;
import com.ryit.goods.service.IBusiGoodsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusiGoodsSkuServiceImpl extends BaseServiceImpl<Long, BusiGoodsSkuDto, BusiGoodsSkuVo> implements IBusiGoodsSkuService {

    @Autowired
    private BusiGoodsSkuMapper busiGoodsSkuMapper;

    @Autowired
    private BusiGoodsPriceMapper busiGoodsPriceMapper;

    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(BusiGoodsSkuDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiGoodsSku po = new BusiGoodsSku().buildPo(dto);
        //当前商品当前最新价格
        int newestPrice = busiGoodsPriceMapper.selectNewestPrice(po.getGoodsId());
        po.setPrice(po.calculatePrice(po, newestPrice));
        po.setCreateUserId(userId);
        po.setCreateDate(new Date());
        return busiGoodsSkuMapper.insertSelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(BusiGoodsSkuDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiGoodsSku po = new BusiGoodsSku().buildPo(dto);
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        return busiGoodsSkuMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return busiGoodsSkuMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<BusiGoodsSkuVo> queryListByCondition(BusiGoodsSkuDto dto) {
        BusiGoodsSku po = new BusiGoodsSku().buildPo(dto);
        List<BusiGoodsSku> poList = busiGoodsSkuMapper.select(po);
        List<BusiGoodsSkuVo> voList = new BusiGoodsSkuVo().buildVoList(poList);
        return voList;
    }

    @Override
    public PageBean<BusiGoodsSkuVo> queryPageList(BaseQueryDto<BusiGoodsSkuDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiGoodsSkuDto dto = queryDto.getParam();
        List<BusiGoodsSkuVo> voList = queryListByCondition(dto);
        return getPageData(voList, page);
    }

    @Override
    public BusiGoodsSkuVo queryById(Long id) {
        BusiGoodsSku po = busiGoodsSkuMapper.selectByPrimaryKey(id);
        return new BusiGoodsSkuVo().buildVo(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BusiGoodsSkuInfoVo> saveSkuCallBack(List<BusiGoodsSkuDto> dtos, HttpServletRequest request) {
        try {
            Integer userId = getCurrentUserId(request);
            //商品规格购买信息
            List<BusiGoodsSkuInfoVo> skuInfoVoList = new ArrayList<>();

            //从规格列表中任意选中一条，取出商品ID（因为商品ID是一样的）
            /*OptionalLong goodsIdOptional = dtos.stream().mapToLong(BusiGoodsSkuDto::getGoodsId).findFirst();
            int newestPrice = 0;
            if (goodsIdOptional.isPresent()) {
                long goodsId = goodsIdOptional.getAsLong();
                //当前商品当前最新价格
                newestPrice = busiGoodsPriceMapper.selectNewestPrice(goodsId);
            }*/
            //组装持久化数组
            List<BusiGoodsSku> poList = new ArrayList<>();
            for (BusiGoodsSkuDto dto : dtos) {
                BusiGoodsSku sku = new BusiGoodsSku().buildPo(dto);
                long skuId = SnowflakeIdWorker.generateId();
                sku.setId(skuId);
                sku.setLength(BigDecimal.valueOf(dto.getLength()).setScale(BigDecimal.ROUND_FLOOR).doubleValue());
                sku.setWidth(BigDecimal.valueOf(dto.getWidth()).setScale(BigDecimal.ROUND_FLOOR).doubleValue());
                sku.setCreateUserId(userId);
                sku.setCreateDate(new Date());
                poList.add(sku);

                //=============收集商品规格购买信息(提交订单时使用)========================
                BusiGoodsSkuInfoVo skuInfoVo = new BusiGoodsSkuInfoVo();
                skuInfoVo.setId(skuId);
                skuInfoVo.setAmount(sku.getAmount());
                skuInfoVo.setPrice(sku.getPrice());
                skuInfoVo.setTotalPrice(sku.getTotalPrice());
                skuInfoVo.setGoodsId(dto.getGoodsId());
                skuInfoVo.setGoodsName(dto.getGoodsName());
                skuInfoVo.setGoodsImg(dto.getGoodsImg());
                skuInfoVoList.add(skuInfoVo);
            }

            //保存规格信息
            busiGoodsSkuMapper.insertBatch(poList);

            return skuInfoVoList;
        } catch (Exception e) {
            throw new CustomException("规格保存失败", e);
        }
    }
}

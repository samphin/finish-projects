package com.ryit.goods.mapper;

import com.ryit.commons.entity.dto.BusiGoodsRolledPriceQueryDto;
import com.ryit.commons.entity.pojo.BusiGoodsRolledPrice;
import com.ryit.commons.entity.vo.BusiGoodsRolledPriceListVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiGoodsRolledPriceMapper extends Mapper<BusiGoodsRolledPrice> {

    /**
     * 查询最新废铁价格
     *
     * @return
     */
    Integer selectNewestScrapIronPrice();

    /**
     * 查询废铁列表
     * @return
     */
    List<BusiGoodsRolledPriceListVo> selectList(BusiGoodsRolledPriceQueryDto queryDto);
}
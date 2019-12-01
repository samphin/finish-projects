package com.ryit.goods.mapper;

import com.ryit.commons.entity.dto.BusiGoodsPcQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsQueryDto;
import com.ryit.commons.entity.pojo.BusiGoods;
import com.ryit.commons.entity.vo.BusiGoodsAppListVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiGoodsMapper extends Mapper<BusiGoods> {

    /**
     * 查询APP商品列表
     *
     * @param queryDto
     * @return
     */
    List<BusiGoodsAppListVo> selectAppGoodsList(BusiGoodsQueryDto queryDto);

    /**
     * 查询PC商品列表
     *
     * @param queryDto
     * @return
     */
    List<BusiGoods> selectGoodsList(BusiGoodsPcQueryDto queryDto);
}
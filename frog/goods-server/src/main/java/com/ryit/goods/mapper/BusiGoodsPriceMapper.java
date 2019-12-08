package com.ryit.goods.mapper;

import com.ryit.commons.entity.dto.BusiGoodsPriceQueryDto;
import com.ryit.commons.entity.pojo.BusiGoodsPrice;
import com.ryit.commons.entity.vo.BusiGoodsPriceListVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiGoodsPriceMapper extends Mapper<BusiGoodsPrice> {

    /**
     * 查询最新商品价格
     *
     * @param goodsId 商品ID
     * @return
     */
    Integer selectNewestPrice(@Param("goodsId") Long goodsId);

    /**
     * 查询商品价格列表
     *
     * @param queryDto
     */
    List<BusiGoodsPriceListVo> selectGoodPriceList(BusiGoodsPriceQueryDto queryDto);
}
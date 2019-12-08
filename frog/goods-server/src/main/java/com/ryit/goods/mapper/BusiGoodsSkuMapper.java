package com.ryit.goods.mapper;

import com.ryit.commons.entity.pojo.BusiGoodsSku;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiGoodsSkuMapper extends Mapper<BusiGoodsSku> {

    /**
     * 批量新增规格
     *
     * @param poList
     * @return
     */
    Integer insertBatch(List<BusiGoodsSku> poList);
}
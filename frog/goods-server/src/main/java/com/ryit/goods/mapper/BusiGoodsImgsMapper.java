package com.ryit.goods.mapper;

import com.ryit.commons.entity.pojo.BusiGoodsImgs;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiGoodsImgsMapper extends Mapper<BusiGoodsImgs> {

    /**
     * 批量新增图片
     *
     * @param poList
     * @return
     */
    Integer insertBatch(List<BusiGoodsImgs> poList);

    /**
     * 删除商品图片
     *
     * @param goodsId
     * @author samphin
     * @since 2019-10-25 11:37:09
     */
    Integer deleteGoodsImages(@Param("goodsId") Long goodsId);

    /**
     * 查询商品所有图片
     * @param goodsId
     * @return
     */
    List<String> selectGoodsImages(@Param("goodsId") Long goodsId);
}
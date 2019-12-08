package com.ryit.order.mapper;

import com.ryit.commons.entity.pojo.BusiOrderGoods;
import com.ryit.commons.entity.vo.BusiOrderGoodsListVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiOrderGoodsMapper extends Mapper<BusiOrderGoods> {

    /**
     * 批量保存订单商品关联信息
     *
     * @param orderGoodsList
     * @return
     */
    Integer insertBatch(List<BusiOrderGoods> orderGoodsList);

    /**
     * 查询订单商品列表
     *
     * @author samphin
     * @since 2019-11-22 09:18:50
     */
    List<BusiOrderGoodsListVo> queryOrderGoodsList(@Param("orderId") Long orderId);
}
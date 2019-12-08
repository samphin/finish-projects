package com.ryit.goods.mapper;

import com.ryit.commons.entity.pojo.BusiGoodsTrolley;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiGoodsTrolleyMapper extends Mapper<BusiGoodsTrolley> {

    /**
     * 批量新增购物车
     *
     * @param poList
     * @return
     */
    Integer insertBatch(List<BusiGoodsTrolley> poList);

    /**
     * 批量删除购物车
     *
     * @param idList
     * @return
     */
    Integer deleteBatch(List<Long> idList);


    /**
     * 查询我的购物车
     *
     * @param userId
     * @return
     */
    List<BusiGoodsTrolley> selectMyTrolley(@Param("userId") Integer userId);

}
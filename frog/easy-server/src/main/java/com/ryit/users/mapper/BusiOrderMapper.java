package com.ryit.users.mapper;

import com.ryit.commons.entity.dto.BusiOrderDto;
import com.ryit.commons.entity.pojo.BusiOrder;
import com.ryit.commons.entity.vo.BusiOrderGoodsVo;
import com.ryit.commons.entity.vo.BusiOrderVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiOrderMapper extends Mapper<BusiOrder> {

    /**
     *  分页查询订单列表
     * @param dto
     * @return
     */
    List<BusiOrderVo> pagelistBusiOrder(BusiOrderDto dto);


    /**
     * 查看订单详情
     * @param orderId
     * @return
     */
    List<BusiOrderGoodsVo> listBusiOrderGoods(Long orderId);

    /**
     * 修改订单部分信息
     * @param dto
     * @return
     */
    Integer updateOrder(BusiOrderDto dto);


}

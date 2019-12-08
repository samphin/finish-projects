package com.ryit.order.mapper;

import com.ryit.commons.entity.dto.BusiOrderQueryDto;
import com.ryit.commons.entity.dto.BusiOrderStatisticDto;
import com.ryit.commons.entity.pojo.BusiOrder;
import com.ryit.commons.entity.vo.BusiOrderDetailVo;
import com.ryit.commons.entity.vo.BusiOrderListVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface BusiOrderMapper extends Mapper<BusiOrder> {

    /**
     * 分页查询订单列表
     *
     * @param dto
     * @return
     */
    List<BusiOrderListVo> selectOrderList(BusiOrderQueryDto dto);


    /**
     * 查询我的拼团订单列表
     *
     * @param goodsId    商品ID
     * @param createDate 我的下单时间
     * @return
     */
    List<BusiOrderListVo> selectMyGroupOrderList(@Param("goodsId") Long goodsId, @Param("createDate") Date createDate);


    /**
     * 查看订单详情
     *
     * @param id
     * @return
     */
    BusiOrderDetailVo selectOrderDetail(@Param("id") Long id);

    /**
     * 查询营业额（总营业额、年营业额、月营业额、日营业额，根据时间筛选）
     *
     * @return
     */
    Double selectTurnover(BusiOrderStatisticDto busiOrderStatisticDto);

    /**
     * 查询订单数量
     *
     * @param userId
     * @return
     */
    Integer selectOrderNumsByUserId(@Param("userId") Integer userId);
}
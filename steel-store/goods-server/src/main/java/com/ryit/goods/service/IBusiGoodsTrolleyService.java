package com.ryit.goods.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyDto;
import com.ryit.commons.entity.dto.BusiGoodsTrolleyUpdateDto;
import com.ryit.commons.entity.vo.BusiGoodsTrolleyVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface IBusiGoodsTrolleyService extends IBaseService<Long, BusiGoodsTrolleyDto, BusiGoodsTrolleyVo> {

    /**
     * 加入购物车
     *
     * @param trolleyDtos
     * @author samphin
     * @since 2019-11-18 10:29:07
     */
    boolean addTrolley(List<BusiGoodsTrolleyDto> trolleyDtos, HttpServletRequest request);


    /**
     * 修改商品购买数量
     *
     * @param dto
     * @return
     */
    boolean updateTrolleyNum(BusiGoodsTrolleyUpdateDto dto, HttpServletRequest request);

    /**
     * 查询我的购物车
     *
     * @param request
     */
    List<BusiGoodsTrolleyVo> queryMyTrolley(HttpServletRequest request);

    /**
     * 清空购购物车
     *
     * @return
     * @author samphin
     */
    boolean empty(HttpServletRequest request);

    /**
     * 删除购物车指定商品
     *
     * @author samphin
     * @since 2019-11-18 16:27:24
     */
    boolean deleteTrolleyGoods(Set<Long> goodsIdList, HttpServletRequest request);
}
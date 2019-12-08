package com.ryit.goods.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.BusiGoodsSkuDto;
import com.ryit.commons.entity.vo.BusiGoodsSkuInfoVo;
import com.ryit.commons.entity.vo.BusiGoodsSkuVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IBusiGoodsSkuService extends IBaseService<Long, BusiGoodsSkuDto, BusiGoodsSkuVo> {

    /**
     * 批量保存商品规格，并返回对应规格ID信息
     *
     * @param dtos
     * @return
     */
    List<BusiGoodsSkuInfoVo> saveSkuCallBack(List<BusiGoodsSkuDto> dtos, HttpServletRequest request);
}

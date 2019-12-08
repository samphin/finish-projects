package com.ryit.goods.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsPriceDto;
import com.ryit.commons.entity.dto.BusiGoodsPriceQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsRolledPriceDto;
import com.ryit.commons.entity.dto.BusiGoodsRolledPriceQueryDto;
import com.ryit.commons.entity.vo.BusiGoodsPriceListVo;
import com.ryit.commons.entity.vo.BusiGoodsPriceVo;
import com.ryit.commons.entity.vo.BusiGoodsRolledPriceListVo;

import javax.servlet.http.HttpServletRequest;

public interface IBusiGoodsPriceService extends IBaseService<Long, BusiGoodsPriceDto, BusiGoodsPriceVo> {

    /**
     * 查询商品价格列表
     *
     * @param queryDto
     * @return
     */
    PageBean<BusiGoodsPriceListVo> queryGoodPriceList(BaseQueryDto<BusiGoodsPriceQueryDto> queryDto);


    /**
     * 新增废铁价格
     *
     * @param dto
     * @param request
     * @return
     */
    boolean saveScrapIronPrice(BusiGoodsRolledPriceDto dto, HttpServletRequest request);

    /**
     * 查询废铁价格列表
     *
     * @author samphin
     * @since 2019-11-26 16:10:59
     */
    PageBean<BusiGoodsRolledPriceListVo> queryScrapIronPriceList(BaseQueryDto<BusiGoodsRolledPriceQueryDto> queryDto);

}
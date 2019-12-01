package com.ryit.goods.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsDto;
import com.ryit.commons.entity.dto.BusiGoodsPcQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsShelveDto;
import com.ryit.commons.entity.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IBusiGoodsService extends IBaseService<Long, BusiGoodsDto, BusiGoodsVo> {


    /**
     * 删除商品图片
     *
     * @author samphin
     * @since 2019-10-25 11:29:45
     */
    boolean deleteImages(long imageId, String imagePath);


    /**
     * 查询所有商品形状列表
     *
     * @return
     */
    List<BusiGoodsShapeListVo> queryGoodsShape();

    /**
     * 查询商品分类列表
     *
     * @return
     */
    List<BusiGoodsBrandListVo> queryGoodsBrand();

    /**
     * 查询商品横拉版宽度
     *
     * @return
     * @author samphin
     */
    List<Double> queryGoodsShapeWidth(Long brandId);

    /**
     * 查询APP商品列表
     *
     * @param queryDto
     * @return
     */
    PageBean<BusiGoodsAppListVo> queryAppGoodsList(BaseQueryDto<BusiGoodsQueryDto> queryDto);

    /**
     * 查询pc端商品列表
     *
     * @param queryDto
     * @return
     */
    PageBean<BusiGoodsListVo> queryGoodsList(BaseQueryDto<BusiGoodsPcQueryDto> queryDto);

    /**
     * 上架｜下架商品
     *
     * @param request
     * @return
     */
    boolean shelve(BusiGoodsShelveDto dto, HttpServletRequest request);

    /**
     * 查看商品详情
     *
     * @author samphin
     * @since 2019-11-25 16:43:17
     */
    BusiGoodsDetailVo queryGoodsDetail(Long goodsId);


}
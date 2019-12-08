package com.ryit.goods.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.bson.BusiGoodsComments;
import com.ryit.commons.entity.dto.BusiGoodsCommentsDto;
import com.ryit.commons.entity.dto.BusiGoodsCommentsQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsCommentsReplyDto;
import com.ryit.commons.entity.vo.BusiGoodsCommentsListVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品评价
 *
 * @author samphin
 * @since 2019-10-25 15:36:56
 */
public interface IBusiGoodsCommentsService {

    /**
     * 添加商品评价信息
     *
     * @author samphin
     * @since 2019-10-25 15:21:24
     */
    boolean insertGoodsComments(BusiGoodsCommentsDto dto, HttpServletRequest request);

    /**
     * 删除指定商品评价信息
     *
     * @author samphin
     * @since 2019-10-25 15:21:24
     */
    boolean deleteGoodsComments(Long id);

    /**
     * 查询APP商品评价
     *
     * @return
     */
    PageBean<BusiGoodsComments> queryAppCommentsList(BaseQueryDto<Long> queryDto);

    /**
     * 按创建时间倒序查询商品所有评价信息
     *
     * @return
     * @author samphin
     * @since 2019-10-25 15:35:52
     */
    PageBean<BusiGoodsCommentsListVo> queryPageGoodsComments(BaseQueryDto<BusiGoodsCommentsQueryDto> queryDto);

    /**
     * 查询我的评价
     *
     * @param queryDto
     * @param request
     * @return
     */
    PageBean<BusiGoodsComments> queryMyComments(BaseQueryDto queryDto, HttpServletRequest request);

    /**
     * 按创建时间倒序查询商品所有评价信息
     *
     * @return
     * @author samphin
     * @since 2019-10-25 15:35:52
     */
    List<BusiGoodsComments> queryGoodsComments(Long goodsId);

    /**
     * 回复评价
     *
     * @param dto 评价信息
     * @return
     */
    boolean replayGoodsComments(BusiGoodsCommentsReplyDto dto, HttpServletRequest request);

}
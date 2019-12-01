package com.ryit.order.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsComplaintDto;
import com.ryit.commons.entity.dto.BusiGoodsComplaintQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsHandlerComplaintDto;
import com.ryit.commons.entity.vo.BusiComplaintVo;

import javax.servlet.http.HttpServletRequest;

public interface IBusiComplaintService extends IBaseService<Long, BusiGoodsComplaintDto, BusiComplaintVo> {

    /**
     * 提交投诉信息
     *
     * @param dto
     * @return
     */
    boolean saveComplaint(BusiGoodsComplaintDto dto, HttpServletRequest request);

    /**
     * 分页查询投诉列表
     *
     * @author samphin
     * @since 2019-11-20 20:20:48
     */
    PageBean queryComplaintList(BaseQueryDto<BusiGoodsComplaintQueryDto> queryDto);

    /**
     * 处理投诉信息
     *
     * @param dto
     * @return
     */
    boolean handlerComplaint(BusiGoodsHandlerComplaintDto dto, HttpServletRequest request);


    /**
     * 查询我的投诉列表
     *
     * @param queryDto 分页对象
     * @param request  请求对象
     * @return
     */
    PageBean<BusiComplaintVo> queryMyComplaints(BaseQueryDto queryDto, HttpServletRequest request);
}
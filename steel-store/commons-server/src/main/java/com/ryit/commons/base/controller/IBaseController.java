package com.ryit.commons.base.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * IBaseController
 *
 * @author : samphin
 * @since : 2019/8/22 10:09
 */
public interface IBaseController<PK extends Serializable, D> {

    /**
     * 新增
     *
     * @param dto
     * @param request
     * @return
     */
    default ResponseData save(D dto, HttpServletRequest request) {
        return null;
    }

    /**
     * 修改
     *
     * @param dto
     * @param request
     * @return
     */
    default ResponseData update(D dto, HttpServletRequest request) {
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    default ResponseData delete(PK id) {
        return null;
    }

    /**
     * 根据条件列表查询
     *
     * @param queryParams
     * @return
     */
    default ResponseData queryListByCondition(D queryParams) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param queryDto
     * @return
     */
    default ResponseData queryPageList(BaseQueryDto<D> queryDto) {
        return null;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    default ResponseData queryOne(PK id) {
        return null;
    }
}

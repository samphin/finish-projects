package com.ryit.commons.base.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公共Service接口类
 *
 * @param <PK> 业务主键
 * @param <D>  业务Dto
 * @param <V>  业务Vo
 * @author samphin
 * @since 2019-10-24 11:34:58
 */
public interface IBaseService<PK, D, V> {

    /**
     * 批量新增
     *
     * @param dtoList
     * @return
     */
    boolean insertBatch(List<D> dtoList);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    boolean insert(D dto);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    boolean insert(D dto, HttpServletRequest httpServletRequest);

    /**
     * 非空新增
     *
     * @param dto
     * @return
     */
    boolean insertSelective(D dto);

    /**
     * 非空新增
     */
    boolean insertSelective(D dto, HttpServletRequest httpServletRequest);

    /**
     * 根据主键修改
     *
     * @param dto
     * @return
     */
    boolean updateById(D dto);

    /**
     * 根据主键修改
     *
     * @param dto
     * @return
     */
    boolean updateById(D dto, HttpServletRequest httpServletRequest);

    /**
     * 根据主键非空修改
     *
     * @param dto
     * @return
     */
    boolean updateByIdSelective(D dto);

    /**
     * 根据主键非空修改
     *
     * @param dto
     * @return
     */
    boolean updateByIdSelective(D dto, HttpServletRequest httpServletRequest);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    boolean deleteById(PK id);

    /**
     * 根据多主键批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteBatch(List<PK> ids);

    /**
     * 无条件查询
     *
     * @return
     */
    List<V> queryList();


    /**
     * 根据条件查询列表
     *
     * @param dto
     * @return
     */
    List<V> queryListByCondition(D dto);

    /**
     * 根据条件查询列表
     *
     * @param dto
     * @return
     */
    List<V> queryListByCondition(D dto, HttpServletRequest request);

    /**
     * 分页查询
     *
     * @param queryDto
     * @return
     */
    PageBean<V> queryPageList(BaseQueryDto<D> queryDto);

    /**
     * 分页查询
     *
     * @param queryDto
     * @return
     */
    PageBean<V> queryPageList(BaseQueryDto<D> queryDto, HttpServletRequest request);

    /**
     * 根据条件求记录数
     *
     * @param dto
     * @return
     */
    int queryCount(D dto);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    V queryById(PK id);
}

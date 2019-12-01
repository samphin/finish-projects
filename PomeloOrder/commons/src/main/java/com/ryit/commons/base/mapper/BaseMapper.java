package com.ryit.commons.base.mapper;

import java.util.List;

/**
 * 公共CRUD方法
 *
 * @param <T>
 * @author samphin
 * @date 2019-9-3 11:47:20
 */
public interface BaseMapper<PK, T> {

    /**
     * 批量新增
     *
     * @param record
     * @return
     */
    int insertBatch(List<T> record);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 非空新增
     *
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 保存
     * @param record
     * @return
     */
    int replace(T record);

    /**
     * 非空保存
     * @param record
     * @return
     */
    int replaceSelective(T record);

    /**
     * 根据主键修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据主键非空修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(PK id);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 根据多主键批量删除
     *
     * @param ids
     * @return
     */
    int deleteBatch(List<PK> ids);


    /**
     * 根据条件查询列表
     *
     * @param record
     * @return
     */
    List<T> selectAllByCondition(T record);
}

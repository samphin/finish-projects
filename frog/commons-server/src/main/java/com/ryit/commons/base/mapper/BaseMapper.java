package com.ryit.commons.base.mapper;

import java.util.List;
import java.util.Map;

/**
 * 公共CRUD方法
 *
 * @param <T>
 * @author samphin
 * @since 2019-9-3 11:47:20
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
     * 保存功能（根据主键如果数据库存在数据则更新，存在新增）
     *
     * @param record
     * @return
     */
    int replace(T record);

    /**
     * 非空保存功能（根据主键如果数据库存在数据则更新，存在新增）
     *
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
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

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
    List<T> selectListByCondition(T record);

    /**
     * 根据条件查询列表
     *
     * @param params
     * @return
     */
    List<T> selectListByMap(Map<String, Object> params);

    /**
     * 根据条件求记录数
     *
     * @param record
     * @return
     */
    Long selectCount(T record);

    /**
     * 根据条件求记录数
     *
     * @param params
     * @return
     */
    Long selectCountByMap(Map<String, Object> params);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(PK id);
}

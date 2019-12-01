package com.ryit.commons.base.service;

import java.util.List;

/**
 * 公共CRUD Service接口
 *
 * @param <D>
 * @author samphin
 * @date 2019-9-3 11:56:10
 */
public interface IBaseService<PK,D, V> {

    /**
     * 保存
     * @param dto
     * @return
     */
    boolean save(D dto);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delete(PK id);

    /**
     * 批量删除
     */
    boolean deleteBatch(List<PK> ids);

    /**
     * 查询列表
     * @param dto
     * @return
     */
    List<V> queryAllByCondition(D dto);

    /**
     * 查询详情
     * @param id
     * @return
     */
    V queryOne(PK id);
}

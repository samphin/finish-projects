package com.ryit.commons.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 公共分页查询返回对象
 *
 * @author samphin
 * @since 2019-10-16 10:35:39
 */
@Data
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = -6506132925546545528L;

    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int pages;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 结果集
     */
    private List<T> list;
}

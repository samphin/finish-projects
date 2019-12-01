package com.ryit.commons.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageSerializable;
import lombok.Data;

import java.util.List;

/**
 * 自定义分页返回对象
 *
 * @author: samphin
 * @create: 2019-9-26 10:59:28
 * @JsonInclude 忽略空值的字段
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PageData extends PageSerializable {

    /**
     * 数据集
     */
    private List<?> rows;

    /**
     * 总页数
     */
    private Integer totalPages;
}

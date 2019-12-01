package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 查询通用字段公共Dto类
 *
 * @author samphin
 * @date 2019-9-2 16:17:39
 */
@Setter
@Getter
@NoArgsConstructor
public class BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 3362034741422988345L;

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询结束时间
     */
    private String endTime;

    /**
     * 页大小，默认第1页
     */
    private Integer pageSize = 10;

    /**
     * 页码值，默认每页显示10条
     */
    private Integer pageNum = 1;
}

package com.ryit.commons.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户回访记录
 */
@Data
public class SysUserCallInfoVo implements Serializable {

    private static final long serialVersionUID = 532515938349696429L;

    /**
     * 回访信息列表
     */
    private List<SysUserCallInfoListVo> dataList = new ArrayList<>();

    /**
     * 回访次数
     */
    private Integer times;
}
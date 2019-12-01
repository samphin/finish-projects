package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 订单金额标准
 *
 * @author samphin
 * @date 2019-9-2 14:50:42
 */
@Setter
@Getter
@NoArgsConstructor
public class SysOrderSettingDto implements Serializable {

    private static final long serialVersionUID = -5012181591250566634L;

    /**
     * 分数下限
     */
    private Integer scopeMin;

    /**
     * 分数上限
     */
    private Integer scopeMax;

    /**
     * 金额
     */
    private Integer score;

}
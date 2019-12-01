package com.ryit.commons.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class SysOrderAptitudeVo implements Serializable {

    private static final long serialVersionUID = 180464011551847788L;
    /**
     * 资质名称
     * isNullAble:1
     */
    private String name;

    /**
     * 资质编码
     * isNullAble:1
     */
    private String code;

    /**
     * 资质值描述
     * isNullAble:1
     */
    private String label;

    /**
     * 资质值
     * isNullAble:1
     */
    private Integer value;

    /**
     * 分数
     * isNullAble:1
     */
    private Integer score;

}

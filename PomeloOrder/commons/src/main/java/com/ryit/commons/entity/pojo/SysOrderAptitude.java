package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class SysOrderAptitude implements Serializable {

    private static final long serialVersionUID = 7147511094081512276L;


    /**
     * 主键
     * isNullAble:0
     */
    private Long id;

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

    /**
     * 优先级0：不显示；1：显示
     * isNullAble:1
     */
    private Integer priority;

    /**
     * 订单编号
     * isNullAble:1
     */
    private Long orderId;
}

package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/29 9:49
 */
@Getter
@Setter
@NoArgsConstructor
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1298670400497897162L;
    /**
     * 地区编码
     */
    private Integer code;
    /**
     * 地区名
     */
    private String areaName;

    /**
     * 地区级别
     */
    private Integer level;

    /**
     * 地区父节点
     */
    private Integer parentCode;

    private List<SysArea> areaList;

}

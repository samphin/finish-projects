package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BusiGoodsShapeDto implements Serializable {

    /**
    * 形状名称
    */
    private String name;

    /**
     * 形状类型编号
     */
    private Integer code;

    /**
    * 图片路径
    */
    private String imgPath;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 创建人ID
    */
    private Integer createUserId;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 最后修改人ID
    */
    private Integer lastUpdateUserId;

    /**
    * 最后修改时间
    */
    private Date lastUpdateDate;
}
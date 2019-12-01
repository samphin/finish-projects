package com.ryit.commons.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当日单价
 *
 * @author samphin
 * @since 2019-10-25 12:42:34
 */
@Data
public class BusiGoodsPriceListVo implements Serializable {

    private static final long serialVersionUID = 3457847289184782497L;

    /**
     * 分类名称
     */
    private String brandName;

    /**
     * 商名称
     */
    private String goodsName;

    /**
     * 当日单价
     */
    private Integer price;

    /**
     * 形状
     */
    private Integer shapeCode;
    /**
     * 默认厚度
     */
    private Integer defaultThickness;
    /**
     * 默认宽度
     */
    private Double defaultWidth;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createDate;
}
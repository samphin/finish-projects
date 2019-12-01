package com.ryit.commons.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 废铁价格类
 *
 * @author samphin
 * @since 2019-11-26 15:38:14
 */
@Data
public class BusiGoodsRolledPrice implements Serializable {

    private static final long serialVersionUID = -3556541159261116812L;

    private Long id;

    /**
     * 当日废铁单价
     */
    private Integer price;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;
}
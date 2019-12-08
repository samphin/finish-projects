package com.ryit.commons.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告图片
 *
 * @author samphin
 * @since 2019-10-31 14:25:40
 */
@Data
public class BusiAdvertImg implements Serializable {

    private static final long serialVersionUID = -5839533986646253790L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 广告id
     */
    private Long advertId;

    /**
     * 图片路径
     */
    private String path;

    /**
     * 图片排序
     */
    private Integer sort;

    /***************操作记录信息 Start*****************/
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
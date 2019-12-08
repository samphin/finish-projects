package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiGoodsBrandDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品类别表
 *
 * @author samphin
 * @since 2019-10-21 17:42:47
 */
@Data
public class BusiGoodsBrand extends BasePo<Long, BusiGoodsBrandDto, BusiGoodsBrand> implements Serializable {

    private static final long serialVersionUID = -8445060426627086726L;
    /**
     * 类别名称
     */
    private String brandName;

    /**
     * 图片路径
     */
    private String imgPath;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 分类级别 1:一级分类 2:二级分类 3:三级分类
     */
    private Integer levels;

    /**
     * 排序
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
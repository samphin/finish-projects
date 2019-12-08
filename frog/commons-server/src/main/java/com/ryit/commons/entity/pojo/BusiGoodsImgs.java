package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiGoodsImgsDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品图片
 *
 * @author samphin
 * @since 2019-10-23 17:33:22
 */
@Data
public class BusiGoodsImgs extends BasePo<Long, BusiGoodsImgsDto, BusiGoodsImgs> implements Serializable {

    private static final long serialVersionUID = 5990519274199700828L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 图片/或者视频路径
     */
    private String filePath;

    /**
     * 文件类型 0 图片 1 视频
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否是图文详情 0否 1是
     */
    private Integer detailsStatus;

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
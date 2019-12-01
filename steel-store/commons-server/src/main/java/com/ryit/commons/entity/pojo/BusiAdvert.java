package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.BusiAdvertDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告表
 *
 * @author samphin
 * @since 2019-10-31 14:35:24
 */
@Data
public class BusiAdvert extends BasePo<Long, BusiAdvertDto, BusiAdvert> implements Serializable {

    private static final long serialVersionUID = 1849336343416199046L;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告分类 0 商品素材 1 滚动横幅 2分层广告 3 三方块广告 4 四方块广告 5邀请海报
     */
    private Integer type;

    /**
     * 广告连接
     */
    private String url;

    /**
     * 是否发布 0否 1是
     */
    private Integer releaseStatus;

    /**
     * 是否有效 0否 1是
     */
    private Integer validStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否是首页广告 0否 1是
     */
    private Integer homeStatus;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

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
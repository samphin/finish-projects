package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiAdvert;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 广告管理
 *
 * @author samphin
 * @since 2019-10-31 14:24:00
 */
@Data
public class BusiHomePageAdvertVo implements Serializable {


    private static final long serialVersionUID = 4522809368335282116L;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告分类 0 商品素材 1 滚动横幅 2分层广告 3 三方块广告 4 四方块广告 5邀请海报
     */
    private Integer type;

    /**
     * 是否发布 0否 1是
     */
    private Integer releaseStatus;

    /**
     * 是否有效 0否 1是
     */
    private Integer validStatus;

    /**
     * 是否是首页广告 0否 1是
     */
    private Integer homeStatus;

    /**
     * 图片地址
     */
    private String imgPath;
}
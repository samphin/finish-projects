package com.ryit.commons.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页信息
 *
 * @author samphin
 * @since 2019-11-12 15:02:57
 */
@Data
public class BusiHomePageVo implements Serializable {

    private static final long serialVersionUID = -1281965379424987975L;

    /**
     * 广告信息
     */
    private List<BusiHomePageAdvertVo> adverts;

    /**
     * 商品类别信息
     */
    private List<BusiGoodsBrandVo> brands;
}
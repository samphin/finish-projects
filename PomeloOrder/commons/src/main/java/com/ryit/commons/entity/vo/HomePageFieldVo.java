package com.ryit.commons.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011上午 10:31
 * @Description: 首页用到的字段归类vo
 */
@Getter
@Setter
@NoArgsConstructor
public class HomePageFieldVo {

    /**
     * 当日订单数量
     */
    private Integer orderCount;

    /**
     * 首页可借的最大额度(沿用字典表中的类型)
     */
    private String maxQuota;
}

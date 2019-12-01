package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户活动对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditActivityDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -6778155287722807955L;

    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动图片地址
     */
    private String imageUrl;

    /**
     * 活动h5页面地址
     */
    private String pageUrl;

    /**
     * 绑定优惠券
     */
    private Long moduleId;

    /**
     * 是否展示活动
     */
    private Integer visible;

    /**
     * 活动有效期开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;

    /**
     * 活动有效期结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
}

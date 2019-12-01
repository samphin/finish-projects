package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditActivityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户活动对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditActivity implements Serializable {

    private static final long serialVersionUID = -4497073186783693605L;

    /**
     * 活动ID
     */
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
     * 优惠券模版ID
     */
    private Long moduleId;

    //*********************优惠券信息
    /**
     * 优惠券名称
     */
    @Transient
    private String moduleName;

    /**
     * 优惠券面值（如果满减券则为满金额，条件值）
     */
    @Transient
    private Double moduleCoin;

    /**
     * 优惠金额面值（针对满减券，折扣值）
     */
    @Transient
    private Double moduleDiscountCoin;

    /**
     * 是否展示活动
     */
    private Integer visible;

    /**
     * 活动有效期开始时间
     */
    private Date validStartTime;

    /**
     * 活动有效期结束时间
     */
    private Date validEndTime;

    public CreditActivity buildPo(CreditActivityDto dto) {
        if (null == dto) {
            return this;
        }
        BeanUtils.copyProperties(dto, this);
        return this;
    }
}

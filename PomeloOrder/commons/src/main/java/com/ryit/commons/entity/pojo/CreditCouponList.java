package com.ryit.commons.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券使用信息列表
 *
 * @author : samphin
 * @Date : 2019-9-4 15:27:15
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponList implements Serializable {

    private static final long serialVersionUID = -1423197537771463243L;

    private Long moduleId;

    private String moduleName;

    private Long userId;

    private String userName;

    private String useStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndTime;
}

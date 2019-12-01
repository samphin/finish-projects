package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消息设置
 * @author samphin
 * @date 2019-9-1 09:15:48
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessageStandardDto implements Serializable {

    private static final long serialVersionUID = 1516967222379059759L;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 工资发放形式
     */
    private Integer wagesWay;

    /**
     * 微粒贷
     */
    private Integer microfinance;

    /**
     * 个人信用
     */
    private Integer personalCredit;

    /**
     * 公积金
     */
    private Integer accumulatioinFund;

    /**
     * 社保
     */
    private Integer socialSecurity;

    /**
     * 房产
     */
    private Integer houseProperty;

    /**
     * 车产
     */
    private Integer carProperty;

    /**
     * 寿险
     */
    private Integer lifeInsurance;

    /**
     * 芝麻信用
     */
    private Integer sesameCredit;

    /**
     * 消息推送开始时间
     */
    private String pushStartTime;

    /**
     * 消息推送结束时间
     */
    private String pushEndTime;

    /**
     * 消息开关
     */
    private Integer messageFlag;
}
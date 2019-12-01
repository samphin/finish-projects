package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * APP首页订单查询Dto
 *
 * @author samphin
 * @date 2019-9-23 20:25:18
 */
@Getter
@Setter
public class SysOrderQueryDto implements Serializable {

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
     * 芝麻信用大于600
     */
    private Integer sesameCreditFine;

    /**
     * 微粒贷
     */
    private Integer microfinance;

    /**
     * 信用卡
     */
    private Integer creditCardFlag;

    //=======================APP首页订单查询字段===================================
    /**
     * 通过资质编码筛选
     */
    private Set<String> aptitudeCode;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 所在城市
     */
    private Integer city;

    /**
     * 工资发放形式
     */
    private Integer wagesWay;

}

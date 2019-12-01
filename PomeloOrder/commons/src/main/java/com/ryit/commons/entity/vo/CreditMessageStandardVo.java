package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditMessageStandard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户订单偏Vo类
 *
 * @author samphin
 * @date 2019-9-1 09:15:48
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessageStandardVo implements Serializable {

    private static final long serialVersionUID = 4734288536430953909L;

    /**
     * 城市编码
     */
    private Integer city;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 工资发放形式
     */
    private Integer wagesWay;

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
     * 微粒贷
     */
    private Integer microfinance;

    /**
     * 个人信用
     */
    private Integer personalCredit;

    /**
     * 消息推送开始时间
     */
    private String pushStartTime;

    /**
     * 消息推送结束时间
     */
    private String pushEndTime;

    /**
     * 消息推送开关
     */
    private Integer messageFlag;

    /**
     * po->vo
     * @param po
     * @return
     */
    public CreditMessageStandardVo buildVo(CreditMessageStandard po) {
        if(null == po){
            po = new CreditMessageStandard();
        }
        BeanUtils.copyProperties(po, this);
        return this;
    }
}
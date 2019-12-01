package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditMessageStandardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * 用户消息设置
 *
 * @author samphin
 * @date 2019-9-1 09:15:48
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessageStandard implements Serializable {


    private static final long serialVersionUID = 6869885186632386978L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 城市编码
     */
    private Integer city = 0;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 消息开关0：关；1：开
     */
    private Integer messageFlag;

    /**
     * 工资发放形式
     */
    private Integer wagesWay = 0;

    /**
     * 公积金
     */
    private Integer accumulatioinFund = 0;

    /**
     * 社保
     */
    private Integer socialSecurity = 0;

    /**
     * 房产
     */
    private Integer houseProperty = 0;

    /**
     * 车产
     */
    private Integer carProperty = 0;

    /**
     * 寿险
     */
    private Integer lifeInsurance = 0;

    /**
     * 芝麻信用
     */
    private Integer sesameCredit = 0;

    /**
     * 微粒贷
     */
    private Integer microfinance = 0;

    /**
     * 个人信用
     */
    private Integer personalCredit = 0;

    /**
     * 消息推送开始时间
     */
    private String pushStartTime;

    /**
     * 消息推送结束时间
     */
    private String pushEndTime;

    public CreditMessageStandard buildPo(Long userId, CreditMessageStandardDto dto) throws ParseException {
        BeanUtils.copyProperties(dto, this);
        this.setUserId(userId);
        return this;
    }
}
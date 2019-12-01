package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.dto.SysOrderTempQueryDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单草稿箱
 * @author samphin
 * @date 2019-10-9 19:43:36
 */
@Getter
@Setter
@ToString
public class SysOrderTemp implements Serializable {

    private static final long serialVersionUID = 8691415300998032753L;

    private Long id;

    /**
     * 贷款金额
     */
    private Double orderMoney;

    /**
     * 贷款期限
     */
    private String orderTerm;

    /**
     * 抢单价格
     */
    private Double orderCoin;

    /**
     * 贷款目的
     */
    private String orderReason;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 贷款用户
     */
    private Long walletUserId;

    /**
     * 最高学历
     */
    private String eduLevel;

    /**
     * 婚姻状况
     */
    private String maritalStatus;

    /**
     * 职业类型
     */
    private String careersType;

    /**
     * 单位性质
     */
    private String companyType;

    /**
     * 月收入
     */
    private String monthCash;

    /**
     * 工资发放形式
     */
    private String wagesWay;

    /**
     * 当前单位工龄
     */
    private String workAge;

    /**
     * 工作城市
     */
    private Integer city;

    /**
     * 单位名称
     */
    private String companyName;

    /**
     * 月经营流水
     */
    private String managerFlow;

    /**
     * 经营年限
     */
    private String managerYear;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 信息来源->1：H5；2：PC端 3：APP
     */
    private Integer source;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 订单资质草稿箱
     */
    @Transient
    private List<SysOrderAptitudeTemp> aptitudes;

    /**
     * queryDto->po
     *
     * @param queryDto
     * @return
     */
    public SysOrderTemp buildPo(SysOrderTempQueryDto queryDto) {
        BeanUtils.copyProperties(queryDto, this);
        return this;
    }

    /**
     * dto->po
     *
     * @param queryDto
     * @return
     */
    public SysOrderTemp buildPo(SysOrderDto queryDto) {
        BeanUtils.copyProperties(queryDto, this);
        return this;
    }
}
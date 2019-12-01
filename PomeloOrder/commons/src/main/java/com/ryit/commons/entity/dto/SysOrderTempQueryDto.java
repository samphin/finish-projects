package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单草稿箱列表查询Dto类
 * @author samphin
 * @date 2019-10-10 14:24:41
 */
@Getter
@Setter
@ToString
public class SysOrderTempQueryDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -4838674406982163096L;
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
     * 贷款用户名称
     */
    private String realname;

    /**
     * 贷款人手机号
     */
    private String phone;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;
}
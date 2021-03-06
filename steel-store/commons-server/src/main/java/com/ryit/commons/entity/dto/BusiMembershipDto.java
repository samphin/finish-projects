package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员表
 *
 * @author samphin
 * @since 2019-10-21 17:45:32
 */
@Data
public class BusiMembershipDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 8241334659480029243L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 会员等级
     */
    private Integer level;

    /**
     * 会员积分
     */
    private Integer bonusPoints;

    /**
     * 会员信用值
     */
    private Integer creditValue;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 可用余额
     */
    private Double money;

    /**
     * 冻结资金
     */
    private Double freezeMoney;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 会员编码
     */
    private String code;

    /**
     * 会员帐号状态: 0:正常 1：异常 2：禁止登录
     */
    private Integer status;

    /**
     * 是否有效0-无效 1-有效
     */
    private Integer validStatus;

    /**
     * 1:我是供应商2：我是采购商3：两者都是
     */
    private Integer tradeStatus;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    //=========================查询条件字段==========================
    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 用户手机号
     */
    private String mobilePhone;

    /**
     * 创建开始时间
     */
    private Date createStartDate;

    /**
     * 创建结束时间
     */
    private Date createEndDate;

}
package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.BusiMembership;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员信息Vo类
 *
 * @author samphin
 * @since 2019-10-21 17:45:32
 */
@Data
public class BusiMembershipVo extends BaseVo<Long, BusiMembership, BusiMembershipVo> implements Serializable {

    private static final long serialVersionUID = -5455324747194217655L;

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
     * 可用余额
     */
    private Double money;

    /**
     * 冻结资金
     */
    private Double freezeMoney;

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
     * 会员创建时间
     */
    private Date createDate;

    /**
     * 登录名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别：0-男 1-女 2-其他
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 身份证号码
     */
    private String idCard;
}
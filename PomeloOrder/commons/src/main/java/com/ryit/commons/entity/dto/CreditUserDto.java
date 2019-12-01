package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : 刘修
 * @Date : 2019/8/25 10:25
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditUserDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -6514505107121336787L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 旧手机号
     */
    private String oldPhone;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 验证码
     */
    private String phoneCode;

    /**
     * 注册开始时间
     */
    private Date registerTimeSta;

    /**
     * 注册结束时间
     */
    private Date registerTimeEnd;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 认证状态(0:未认证1:认证中2:已认证)
     */
    private Integer authFlag;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户性别（0女 1 男）
     */
    private Integer sex;

    /**
     * 所在城市
     */
    private Integer city;

    /**
     * 公司类型
     */
    private String companyType;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddr;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 住址
     */
    private String realAddr;

    /**
     * 身份证号
     */
    private String realCode;

    /**
     * 有效期
     */
    private String activeLife;

    /**
     * 签发机关
     */
    private String issuingUnit;

    /**
     * 与公司LOGO合影
     */
    private String groupPhoto;

    /**
     * 工牌
     */
    private String workCard;

    /**
     * 名片
     */
    private String businessCard;

    /**
     * 合同签字页图片
     */
    private String contractSignature;

    /**
     * 消息推送开关
     */
    private Integer messageFlag;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请用户
     */
    private Long upUser;

    /**
     * 是否管理员(0:不是1:是)
     */
    private Integer adminFlag;

    /**
     * 退单次数
     */
    private Integer backOrderNum;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 成功邀请人数
     */
    private Integer inviteNum;

}

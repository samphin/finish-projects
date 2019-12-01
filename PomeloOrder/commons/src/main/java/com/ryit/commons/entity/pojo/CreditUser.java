package com.ryit.commons.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * credit_user
 *
 * @author
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditUser implements Serializable {
    private static final long serialVersionUID = -3532359118027097816L;
    /**
     * 用户ID
     */
    private Long id;

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
     * 抢单币
     */
    private Double remainCoin;

    /**
     * 用户性别（0女 1 男）
     */
    private Integer sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 认证状态(0:未认证1:认证中2:已认证)
     */
    private Integer authFlag;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;

    /**
     * 成功邀请人数
     */
    private Integer inviteNum;

    /**
     * 微信OpenID
     */
    private String openId;


}
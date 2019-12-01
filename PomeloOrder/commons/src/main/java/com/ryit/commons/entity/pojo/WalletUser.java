package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * wallet_user
 *
 * @author
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletUser implements Serializable {

    private static final long serialVersionUID = 2937756842274691865L;

    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 是否管理员(0:不是1:是)
     */
    private Integer adminFlag;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;


    /**
     * 是否推送消息
     */
    private Integer messageFlag;

    /**
     * 性别 0 女 1 男
     * isNullAble:1
     */
    private Integer sex;

    /**
     * 身份证号
     * isNullAble:1
     */
    private String creditorIdcard;

    /**
     * 真实姓名
     * isNullAble:1
     */
    private String realname;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户注册时间
     */
    private Date releaseTime;

    /**
     * 微信登陆的openId
     */
    private String openId;

    /**
     * 用户来源编码
     */
    private String sourceCode;

    /**
     * 用户来源描述
     */
    private String sourceName;

    /**
     * 用户来源地址
     */
    private String sourceUrl;
}
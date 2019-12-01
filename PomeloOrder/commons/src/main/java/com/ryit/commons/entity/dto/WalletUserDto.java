package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/9 0009上午 9:21
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletUserDto extends BaseQueryDto implements Serializable {


    private static final long serialVersionUID = -2667066432962353096L;

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
     * 性别(0女1男)
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

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
     * 所在城市名
     */
    private String areaName;

    /**
     * 验证码
     */
    private String phoneCode;

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
     * 用户注册时间
     */
    private Date releaseTime;

    /**
     * 微信登陆的openId
     */
    private String openId;


    /**
     * 添加后台管理员时赋予管理员角色id
     */
    private Long roleId;
}

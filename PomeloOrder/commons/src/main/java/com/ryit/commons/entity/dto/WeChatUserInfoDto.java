package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信用户信息
 *
 * @author samphin
 * @date 2019-9-16 11:40:46
 */
@Setter
@Getter
@NoArgsConstructor
public class WeChatUserInfoDto implements Serializable {

    private static final long serialVersionUID = -9005012006862948149L;

    // 用户的标识
    private String openId;
    // 昵称
    private String nickname;
    // 用户的性别（1是男性，2是女性，0是未知）
    private int sex;
    // 用户头像
    private String headImgUrl;
    // 手机号
    private String phone;
    //密码
    private String password;
    //手机验证码
    private String phoneCode;
}

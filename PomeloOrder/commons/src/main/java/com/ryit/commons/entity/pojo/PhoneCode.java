package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @program: whsoso
 * @description:
 * @author: Mr.Lu
 * @create: 2019-07-25 17:09
 **/
@Setter
@Getter
@NoArgsConstructor
public class PhoneCode implements Serializable {

    private static final long serialVersionUID = 6627499233619194300L;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 有效期
     */
    private Long time;
}

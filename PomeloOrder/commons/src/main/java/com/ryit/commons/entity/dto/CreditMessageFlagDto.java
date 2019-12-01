package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户消息设置
 * @author samphin
 * @date 2019-9-1 09:15:48
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessageFlagDto implements Serializable {

    private static final long serialVersionUID = -5837039456202470096L;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 是否开启消息推送，true开，false关
     */
    private Boolean messageFlag;
}
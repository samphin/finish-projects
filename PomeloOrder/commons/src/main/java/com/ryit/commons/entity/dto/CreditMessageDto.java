package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 历史消息
 * @author samphin
 * @date 2019-9-1 09:15:22
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessageDto implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 推送时间
     */
    private Date pushTime;

}
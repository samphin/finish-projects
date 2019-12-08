package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 极光推送消息Dto
 *
 * @author samphin
 * @since 2019年9月28日16:01:41
 */
@Data
public class PushMessageDto implements Serializable {

    private static final long serialVersionUID = 3507225937311039188L;
    
    /**
     * 别名
     */
    private String alias;

    /**
     * 别名集合
     */
    private List<String> aliasList;

    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 目标APP名称选填【】
     */
    private String targetApp;
}

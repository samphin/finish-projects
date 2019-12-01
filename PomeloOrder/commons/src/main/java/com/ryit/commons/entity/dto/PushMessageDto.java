package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 极光推送消息模版类
 *
 * @author samphin
 * @date 2019-9-30 10:42:08
 */
@Data
public class PushMessageDto implements Serializable {

    private static final long serialVersionUID = 8935946106266534769L;
    /**
     * 消息对应用户ID
     */
    private List<?> users;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息所属APP名称 APP类型：credit或wallet
     */
    private String appType;
}

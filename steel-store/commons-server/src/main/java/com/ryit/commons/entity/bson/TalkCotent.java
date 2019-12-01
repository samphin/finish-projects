package com.ryit.commons.entity.bson;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 客服聊天记录
 *
 * @author samphin
 * @since 2019-10-21 09:55:04
 */
@Document(collection = "talk_content")
public class TalkCotent implements Serializable {

    private static final long serialVersionUID = -6467237736460433457L;

    @Id
    private ObjectId id;

    /**
     * 内容
     */
    private String sendContent;

    /***************操作记录信息 Start*****************/
    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改人ID
     */
    private Integer lastUpdateUserId;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;

}

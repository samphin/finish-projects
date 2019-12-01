package com.ryit.commons.entity.dto;

import com.ryit.commons.entity.pojo.CreditNotesImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author samphin
 * @date 2019-8-29 17:18:24
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditNotesDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -410721627046838128L;

    private Long id;

    /**
     * 留言
     */
    private String note;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 回复
     */
    private String answerContent;

    /**
     * 回复人ID
     */
    private Long answerUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 回复时间
     */
    private Date answerTime;

    /**
     * 回复状态
     */
    private Integer answerStatus;

    /**
     * 多图片上传
     */
    private List<String> images;

}
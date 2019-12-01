package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditNotesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;

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
public class CreditNotes implements Serializable {

    private static final long serialVersionUID = 2683316507283152160L;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 回复
     */
    private String answerContent;

    /**
     * 回复人ID
     */
    private Long answerUserId;

    /**
     * 回复人名称
     */
    @Transient
    private String answerUserName;

    /**
     * 回复时间
     */
    private Date answerTime;

    /**
     * 回复状态：默认0:未回复;1:已回复
     */
    private Boolean answerStatus;

    /**
     * 留言图片
     */
    private List<CreditNotesImg> images;

    /**
     * dto->po
     *
     * @param dto
     * @return
     */
    public CreditNotes buildPo(CreditNotesDto dto) {
        if (null == dto) {
            return this;
        }
        BeanUtils.copyProperties(dto, this);
        if(null != dto.getAnswerStatus()){
            this.setAnswerStatus(1 == dto.getAnswerStatus() ? true : false);
        }
        return this;
    }
}
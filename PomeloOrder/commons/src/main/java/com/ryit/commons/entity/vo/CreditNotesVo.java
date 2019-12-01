package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditNotes;
import com.ryit.commons.entity.pojo.CreditNotesImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author samphin
 * @date 2019-8-29 17:18:24
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditNotesVo implements Serializable {

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
     * 回复人名称
     */
    private String answerUserName;

    /**
     * 回复时间
     */
    private Date answerTime;

    /**
     * 回复状态：默认0:未回复;1:已回复
     */
    private Integer answerStatus;

    /**
     * 留言图片
     */
    private List<String> imageUrlList = new ArrayList<>();

    /**
     * dto->po
     */
    public static CreditNotesVo buildVo(CreditNotes po) {
        CreditNotesVo vo = new CreditNotesVo();
        BeanUtils.copyProperties(po, vo);
        vo.setAnswerStatus(po.getAnswerStatus() ? 1 : 0);
        if (!CollectionUtils.isEmpty(po.getImages())) {
            vo.setImageUrlList(po.getImages().stream().map(CreditNotesImg::getImgPath).collect(Collectors.toList()));
        }
        return vo;
    }

    public static List<CreditNotesVo> buildVoList(List<CreditNotes> poList) {
        List<CreditNotesVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            voList.add(buildVo(po));
        });
        return voList;
    }
}
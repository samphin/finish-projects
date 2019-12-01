package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 历史消息
 *
 * @author samphin
 * @date 2019-9-1 09:15:22
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessageVo implements Serializable {

    private static final long serialVersionUID = 8934750594375302931L;

    private Long id;

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

    /**
     * 消息读状态已读：1，未读：0
     */
    private Integer readStatus;

    /**
     * 将poList->voList
     *
     * @param poList
     * @return
     */
    public static List<CreditMessageVo> buildVoList(List<CreditMessage> poList) {

        List<CreditMessageVo> voList = new ArrayList<>();

        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            CreditMessageVo vo = new CreditMessageVo();
            BeanUtils.copyProperties(po, vo);
            vo.setReadStatus(po.getReadStatus() ? 1 : 0);
            voList.add(vo);
        });
        return voList;
    }

}
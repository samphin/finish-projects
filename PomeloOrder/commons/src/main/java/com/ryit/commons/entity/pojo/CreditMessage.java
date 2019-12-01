package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditMessageDto;
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
 * @author samphin
 * @date 2019-9-1 09:15:22
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditMessage implements Serializable {

    private static final long serialVersionUID = 5599506155127765474L;

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
     * 消息读状态已读：true，未读：false
     */
    private Boolean readStatus;

    /**
     * @param messageDtoList
     * @return
     */
    public List<CreditMessage> buildPoList(List<CreditMessageDto> messageDtoList){
        List<CreditMessage> poList = new ArrayList<>();
        if(CollectionUtils.isEmpty(messageDtoList)){
            return null;
        }
        messageDtoList.forEach(dto->{
            CreditMessage po = new CreditMessage();
            BeanUtils.copyProperties(dto,po);
            poList.add(po);
        });

        return poList;
    }

}
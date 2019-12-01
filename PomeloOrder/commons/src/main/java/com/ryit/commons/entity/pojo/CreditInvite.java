package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditInviteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author : 刘修
 * @Date : 2019/8/28 15:05
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditInvite implements Serializable {

    private static final long serialVersionUID = -871034579860838301L;

    private Long id;
    /**
     * 奖励金额
     */
    private Double coin;
    /**
     * 人数
     */
    private Integer num;

    public CreditInvite buildPo(CreditInviteDto dto) {
        if(null == dto){
            return this;
        }
        BeanUtils.copyProperties(dto, this);
        return this;
    }
}

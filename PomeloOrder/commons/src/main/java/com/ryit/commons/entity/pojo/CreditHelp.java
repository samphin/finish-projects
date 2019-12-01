package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditHelpDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author samphin
 * @date 2019-8-29 17:18:24
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditHelp implements Serializable {

    private static final long serialVersionUID = 2270904394574069586L;

    private Long id;

    private String ask;

    private Integer sort;

    private String answer;

    public CreditHelp buildPo(CreditHelpDto dto) {
        if(null == dto){
            return this;
        }
        BeanUtils.copyProperties(dto, this);
        return this;
    }
}
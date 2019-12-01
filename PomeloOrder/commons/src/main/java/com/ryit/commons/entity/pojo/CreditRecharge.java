package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditRechargeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author : samphin
 * @Date : 2019-9-3 15:43:25
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditRecharge implements Serializable {

    private static final long serialVersionUID = -8489633873245487978L;

    private Long id;

    /**
     * 充值金额
     */
    private Double money;
    /**
     * 赠送金额
     */
    private Double gift;
    /**
     * 充值方案(0:首充1:重复充值)
     */
    private Integer rechargeType;

    public CreditRecharge buildPo(CreditRechargeDto dto) {
        BeanUtils.copyProperties(dto, this);
        return this;
    }
}

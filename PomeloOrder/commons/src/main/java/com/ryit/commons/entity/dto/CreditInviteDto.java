package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : samphin
 * @Date : 2019-9-3 11:57:16
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditInviteDto implements Serializable {


    private static final long serialVersionUID = 143171298996128423L;

    private Long id;

    /**
     * 奖励金额
     */
    private Double coin;
    /**
     * 人数
     */
    private Integer num;

}

package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 流水账单查询对象
 *
 * @author : samphin
 * @Date : 2019-9-2 15:38:00
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditBillQueryDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 4624230310138859446L;

    /**
     * 用户名(用于显示用户名)
     */
    private String userName;

    /**
     * 账单类型(0:充值1:抢单2:退单)
     */
    private Integer billType;

}

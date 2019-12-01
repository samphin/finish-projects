package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 汇总查询对象值
 * @author samphin
 * @date  2019-9-2 20:08:28
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditBillStatistics implements Serializable {

    private static final long serialVersionUID = -8585759116529267589L;

    private Long totalCount;

    private Double totalCoin;

    private Double totalRechargeMoney;

    private Integer billType;
}

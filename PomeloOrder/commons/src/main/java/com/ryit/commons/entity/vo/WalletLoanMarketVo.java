package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletLoanMarket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: zhangweixun
 * @Date: 2019/10/17 0017下午 4:18
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletLoanMarketVo extends WalletLoanMarket {

    /**
     * 城市名
     */
    private String areaName;

    /**
     * 城市父code
     */
    private String parentCode;
}

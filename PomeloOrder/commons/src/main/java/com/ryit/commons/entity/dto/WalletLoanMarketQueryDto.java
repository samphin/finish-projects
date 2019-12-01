package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005下午 2:16
 * @Description：钱包贷款超市条件查询封装类
 */
@Setter
@Getter
@NoArgsConstructor
public class WalletLoanMarketQueryDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -5012181591250566634L;

    /**
     * 是否推荐(0:否1:是)
     */
    private Integer recommend;

    /**
     * 贷款类别(1:100%下款2:拒就赔钱3:芝麻分贷4:新贷推荐)
     */
    private String loanCategory;

    /**
     * 是否下款快(0:否1:是)
     */
    private Integer fastFlag;

    /**
     * 是否额度高(0:否1:是)
     */
    private Integer highFlag;

    /**
     * 是否期限长(0:否1:是)
     */
    private Integer longFlag;

    /**
     * 是否高通过(0:否1:是)
     */
    private Integer passFlag;

    /**
     * 是否利息低(0:否1:是)
     */
    private Integer lowFlag;

    /**
     * 是否上新(0:否1:是)
     */
    private Integer newFlag;

    /**
     * 超市所在地区
     */
    private Integer city;

    /**
     * 0推荐 1下款快 2额度高 3期限长 4高通过 5利息低
     */
    private Integer status;

    /**
     * 地区名
     */
    private String areaName;
}

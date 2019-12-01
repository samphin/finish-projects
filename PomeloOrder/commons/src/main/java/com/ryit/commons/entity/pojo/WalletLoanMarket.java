package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005上午 11:18
 * @Description： 七一钱包贷款超市
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletLoanMarket implements Serializable{

    private static final long serialVersionUID = 2937756842274691865L;

    /**
     * 主键
     *
     * isNullAble:0
     */
    private Long id;

    /**
     * 贷款名
     * isNullAble:1
     */
    private String loanName;

    /**
     * 额度范围起
     * isNullAble:1
     */
    private Double quotaStart;

    /**
     * 额度范围止
     * isNullAble:1
     */
    private Double quotaEnd;

    /**
     * 是否推荐(0:否1:是)
     * isNullAble:1
     */
    private Integer recommend;

    /**
     * 贷款类别(1:100%下款2:拒就赔钱3:芝麻分贷4:新贷推荐)
     * isNullAble:1
     */
    private String loanCategory;

    /**
     * 是否下款快(0:否1:是)
     * isNullAble:1
     */
    private Integer fastFlag;

    /**
     * 是否额度高(0:否1:是)
     * isNullAble:1
     */
    private Integer highFlag;

    /**
     * 是否期限长(0:否1:是)
     * isNullAble:1
     */
    private Integer longFlag;

    /**
     * 是否高通过(0:否1:是)
     * isNullAble:1
     */
    private Integer passFlag;

    /**
     * 是否利息低(0:否1:是)
     * isNullAble:1
     */
    private Integer lowFlag;

    /**
     * 是否上新(0:否1:是)
     * isNullAble:1
     */
    private Integer newFlag;

    /**
     * 描述
     * isNullAble:1
     */
    private String description;

    /**
     * 贷款描述
     */
    private String labelDesc;

    /**
     * 第三方H5链接
     * isNullAble:1
     */
    private String urlLink;

    /**
     * 月利率
     * isNullAble:1
     */
    private Double monthlyRate;

    /**
     * 贷款期限
     * isNullAble:1
     */
    private String loanTerm;

    /**
     * 放款速度
     * isNullAble:1
     */
    private String loanSpeed;

    /**
     * 申请条件
     * isNullAble:1
     */
    private String applyConditions;

    /**
     * 产品LOGO
     * isNullAble:1
     */
    private String icon;

    /**
     * 钱包贷款超市所在地区
     */
    private Integer city;
}

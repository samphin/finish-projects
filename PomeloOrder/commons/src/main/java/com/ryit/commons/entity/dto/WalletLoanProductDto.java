package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: zhangweixun
 * @Date: 2019/10/18 0018上午 11:32
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletLoanProductDto extends BaseQueryDto {

    /**
     * 主键
     *
     * isNullAble:0
     */
    private Long id;

    /**
     * 产品名称
     * isNullAble:1
     */
    private String productName;

    /**
     * 最高额度
     * isNullAble:1
     */
    private Double maxQuota;

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
     * 前置图标
     * isNullAble:1
     */
    private String icon;

    /**
     * 产品标签
     * isNullAble:1
     */
    private String label;

    /**
     * 客户类别
     * isNullAble:1
     */
    private String customType;

    /**
     * 收入要求
     * isNullAble:1
     */
    private String incomeRequire;

    /**
     * 工作年限
     * isNullAble:1
     */
    private String workYears;

    /**
     * 到账速度说明
     * isNullAble:1
     */
    private String arrivalRate;

    /**
     * 日利率
     * isNullAble:1
     */
    private Double dailyRate;

    /**
     * 信用状况说明
     * isNullAble:1
     */
    private String creditInfo;

    /**
     * 年龄区间
     * isNullAble:1
     */
    private Integer age;

    /**
     * 提前还款手续费说明
     * isNullAble:1
     */
    private String repaymentFee;

    /**
     * 月供
     * isNullAble:1
     */
    private Double monthlySupply;

    /**
     * 费用说明
     * isNullAble:1
     */
    private String costDescription;

    /**
     * 总费用
     * isNullAble:1
     */
    private Double totalCost;

    /**
     * 星级 1分=半星
     * isNullAble:1
     */
    private Integer level;

    /**
     * 申请条件
     * isNullAble:1
     */
    private String applicationConditions;
}

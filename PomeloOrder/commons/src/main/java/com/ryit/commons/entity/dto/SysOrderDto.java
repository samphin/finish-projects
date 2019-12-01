package com.ryit.commons.entity.dto;

import com.ryit.commons.entity.pojo.SysOrderAptitude;
import com.ryit.commons.entity.pojo.WalletUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/20 11:23
 * @updater samphin
 * @lastUpdateDate 2019-9-5 16:40:27
 */
@Getter
@Setter
public class SysOrderDto extends BaseQueryDto implements Serializable {


    private static final long serialVersionUID = -6514505107121336787L;

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 信贷员用户id
     */
    private Long creditUserId;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 退单图片数组
     */
    private String[] backImgs;

    /**
     * 贷款金额最小值
     */
    private Double orderMoneyMin;

    /**
     * 贷款金额最大值
     */
    private Double orderMoneyMax;

    /**
     * 抢单价格最小值
     */
    private Double orderCoinMin;

    /**
     * 抢单价格最大值
     */
    private Double orderCoinMax;

    /**
     * 贷款金额
     */
    private Double orderMoney;

    /**
     * 贷款期限
     */
    private String orderTerm;

    /**
     * 贷款目的
     */
    private String orderReason;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 是否被抢0:否1:是)
     */
    private Integer orderStatus;

    /**
     * 是否通话(0:否1:是)
     */
    private Integer callFlag;

    /**
     * 是否删除(0:否1:是)
     */
    private Integer delFlag;

    /**
     * 是否完结(0:否1:是)
     */
    private Integer finishFlag;

    /**
     * 抢单价格
     */
    private Double orderCoin;

    /**
     * 发布人ID
     */
    private Long releaseUserId;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    /**
     * 发布开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseStartTime;

    /**
     * 发布结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseEndTime;

    /**
     * 贷款用户
     */
    private Long walletUserId;

    /**
     * 抢单账单id
     */
    private Long payId;

    /**
     * 抢单时间
     */
    private Date payTime;

    /**
     * 退单账单id
     */
    private Long backId;

    /**
     * 退单时间
     */
    private Date backTime;

    /**
     * 退单状态(0:未退单1:退单审核2:退单成功3:退单失败)
     */
    private Integer backStatus;

    /**
     * 退单原因
     */
    private String backReason;

    /**
     * 退单失败原因
     */
    private String errorReason;

    /**
     * 订单所属产品id
     */
    private Long productId;

    /**
     * 订单信息来源（1：H5；2：PC管理后台 3：APP）
     */
    private Integer source;

    /**
     * 来源网站编码
     */
    private String sourceCode;

    /**
     * 来源网站简称
     */
    private String sourceName;

    /**
     * 来源网站地址
     */
    private String sourceUrl;

    //======================基本信息=============================
    /**
     * 最高学历
     * isNullAble:1
     */
    private String eduLevel;

    /**
     * 婚姻状况
     * isNullAble:1
     */
    private String maritalStatus;

    /**
     * 职业类型
     * isNullAble:1
     */
    private String careersType;

    /**
     * 单位性质
     * isNullAble:1
     */
    private String companyType;

    /**
     * 月收入
     * isNullAble:1
     */
    private String monthCash;

    /**
     * 工资发放形式
     * isNullAble:1
     */
    private String wagesWay;

    /**
     * 当前单位工龄
     * isNullAble:1
     */
    private String workAge;

    /**
     * 工作城市
     * isNullAble:1
     */
    private Integer city;

    /**
     * 单位名称
     * isNullAble:1
     */
    private String companyName;

    /**
     * 月经营流水
     * isNullAble:1
     */
    private String managerFlow;

    /**
     * 经营年限
     * isNullAble:1
     */
    private String managerYear;

    /**
     * 营业执照
     * isNullAble:1
     */
    private String businessLicense;

    /**
     * 订单用户资质信息
     */
    private List<SysOrderAptitude> aptitudes;

    /**
     * 贷款用户基本信息
     */
    private WalletUser userInfo;
}

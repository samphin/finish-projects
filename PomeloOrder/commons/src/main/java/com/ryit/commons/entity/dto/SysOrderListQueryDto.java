package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * PC端管理后台订单列表查询Dto
 *
 * @author samphin
 * @date 2019-10-18 09:13:01
 */
@Getter
@Setter
public class SysOrderListQueryDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 7538496279609658524L;

    /**
     * 信贷员用户id
     */
    private Long creditUserId;

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
     * 发布人名称
     */
    private String releaseUserName;

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
     * 贷款用户名称
     */
    private String realName;

    /**
     * 贷款人手机号
     */
    private String phone;

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
     * 订单信息来源（1：H5；2：PC管理后台 3：APP）
     */
    private Integer source;

}

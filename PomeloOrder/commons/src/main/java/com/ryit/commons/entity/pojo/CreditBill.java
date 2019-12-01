package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 刘修
 * @date: 2019/8/24 10:34
 * Description:
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditBill implements Serializable {
    private static final long serialVersionUID = -9194665027259564164L;


    /**
     * 主键
     */
    private Long id;
    /**
     *  用户
     */
    private Long userId;
    /**
     * 用户名(用于显示用户名)
     */
    private String userName;
    /**
     *  金额
     */
    private Double billCoin;
    /**
     *  账单类型(0:充值1:抢单2:退单)
     */
    private Integer billType;

    /**
     * 账单类型名称
     */
    private String billTypeName;
    /**
     *  账单时间
     */
    private Date billTime;
    /**
     *  优惠券id
     */
    private Long couponId;
    /**
     * 充值金额
     */
    private Double billMoney;


}
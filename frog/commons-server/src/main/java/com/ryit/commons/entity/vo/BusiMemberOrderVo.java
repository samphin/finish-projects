package com.ryit.commons.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员订单统计Vo类
 *
 * @author samphin
 * @since 2019-10-29 17:05:52
 */
@Data
public class BusiMemberOrderVo implements Serializable {

    private static final long serialVersionUID = 4362797531969118141L;

    /**
     * 会员真实姓名
     */
    private String memberRealName;

    /**
     * 会员订单数量
     */
    private String memberOrderNum;
}

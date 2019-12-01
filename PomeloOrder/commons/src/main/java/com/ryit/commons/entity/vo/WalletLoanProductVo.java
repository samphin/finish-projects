package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletLoanProduct;
import com.ryit.commons.entity.pojo.WalletTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/20 0020下午 5:00
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletLoanProductVo extends WalletLoanProduct {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单提交时间
     */
    private Date releaseTime;

    /**
     * 订单提交时间的格式化
     */
    private String time;

    /**
     * 是否被抢(0:否1:是)
     */
    private Integer orderStatus;

    /**
     * 商品标签
     */
    private List<WalletTag> list;
}








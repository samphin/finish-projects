package com.ryit.credituserserver.wechat;

import lombok.Data;
import org.apache.commons.lang.time.DateFormatUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

/**
 * 微信支付参数类（根据业务需求不同自我调整）
 *
 * @author samphin
 * @date 2019-9-22 11:26:08
 */
public class WeChatPayParam {

    /**
     * 交易内容
     */
    private String body;

    /**
     * 支付总金额
     */
    private String totalFee;

    public WeChatPayParam(){}

    public WeChatPayParam(String body){
        this.body = body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    /**
     * 根据支付金额计算支付总金额
     * @param money
     */
    public void setTotalFee(Double money){
        //支付总金额（不需要四舍五入设置成RoundingMode.DOWN）
        BigDecimal payAmount = BigDecimal.valueOf(money).setScale(2, RoundingMode.UP);
        this.totalFee = Objects.toString(payAmount.multiply(BigDecimal.valueOf(100)).intValue());
    }

    public void setTotalFee(String totalFee) {
        /**
         * 微信支付的金额是String类型 并且是以分为单位
         * 下面举个例子单位是元是怎么转为分的
         */
        BigDecimal totalPrice = new BigDecimal(1); //此时的单位是元
        this.totalFee = totalPrice.multiply(new BigDecimal(100)).toBigInteger().toString();
    }

    /**
     * 获取支付总金额
     * @return
     */
    public String getTotalFee() {
        return this.totalFee;
    }

    /**
     * 随机数字字符串
     */
    public String getOutTradeNo() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS");
    }
}

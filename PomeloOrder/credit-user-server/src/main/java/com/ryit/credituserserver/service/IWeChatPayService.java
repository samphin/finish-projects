package com.ryit.credituserserver.service;

import com.ryit.commons.entity.dto.WeChatOrderQueryDto;
import com.ryit.commons.entity.dto.WeChatRefundDto;
import com.ryit.commons.entity.dto.WeChatUnifiedOrderDto;

import java.util.Map;

/**
 * <p>
 * 微信支付接口实现类
 * </p>
 *
 * @author away-
 * @since 2019-09-18
 */
public interface IWeChatPayService {

    /**
     * APP统一下单
     *
     * @author samphin
     * @date 2019-9-20 14:43:21
     */
    Map<String, String> unifiedOrderByApp(WeChatUnifiedOrderDto unifiedOrderDto);

    /**
     * 查询微信订单
     * @param out_trade_no  商户订单号
     * @author samphin
     * @date 2019-9-22 09:15:23
     */
    Map<String, String> queryWeChatOrder(String out_trade_no);

    /**
     * 小程序统一下单
     *
     * @author samphin
     * @since 2019-9-20 14:42:56
     */
    Map<String, String> unifiedOrderByApplet(WeChatUnifiedOrderDto unifiedOrderDto);

    /**
     * 申请退款
     *
     * @author samphin
     * @since 2019-9-20 14:44:00
     */
    Map<String, String> refund(WeChatRefundDto refundDto);

    /**
     * 校验签名
     *
     * @param requestData
     * @author chneyongfeng
     * @date 2019-9-22 12:40:53
     */
    boolean isSignatureValid(Map<String, String> requestData);

}

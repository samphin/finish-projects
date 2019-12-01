package com.ryit.credituserserver.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.WeChatOrderQueryDto;
import com.ryit.commons.entity.dto.WeChatRefundDto;
import com.ryit.commons.entity.dto.WeChatUnifiedOrderDto;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credituserserver.config.MyWeChatPayConfig;
import com.ryit.credituserserver.service.IWeChatPayService;
import com.ryit.credituserserver.wechat.WeChatPayParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信支付业务处理类
 *
 * @author samphin
 * @date 2019-9-22 11:25:25
 */
@Service
public class WeChatPayServiceImpl implements IWeChatPayService {

    /**
     * 微信配置信息
     *
     * @author samphin
     * @date 2019-9-22 09:08:28
     */
    @Autowired
    private MyWeChatPayConfig myWeChatPayConfig;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 微信APP支付
     *
     * @param unifiedOrderDto 下单信息
     */
    @Override
    public Map<String, String> unifiedOrderByApp(WeChatUnifiedOrderDto unifiedOrderDto) {
        //返回给APP端的参数，APP端再调起支付接口
        Map<String, String> payParamData = new HashMap<>();
        try {
            WeChatPayParam weChatPayParam = new WeChatPayParam();
            weChatPayParam.setBody("柚子抢单-充值");
            weChatPayParam.setTotalFee(unifiedOrderDto.getMoney());
            //初始化微信支付对象
            WXPay wxPay = new WXPay(myWeChatPayConfig);
            //充值金额
            double money = unifiedOrderDto.getMoney();
            //赠送金额
            double gift = unifiedOrderDto.getGift();
            //根据微信支付api来设置
            Map<String, String> data = new HashMap<>();
            data.put("trade_type", "APP");                         //支付场景 APP 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付
            data.put("notify_url", myWeChatPayConfig.getNotify_url());                     //回调地址，到时候需要换成处理业务的回调接口
            data.put("spbill_create_ip", myWeChatPayConfig.getSpbill_create_ip());             //终端ip
            data.put("total_fee", weChatPayParam.getTotalFee());       //订单总金额
            data.put("fee_type", "CNY");                           //默认人民币
            String outTradeNo = weChatPayParam.getOutTradeNo();
            data.put("out_trade_no", outTradeNo);   //商户订单号
            //将商户号作为key，缓存redis中，默认给为0，代表支付未成功
            long expireTime = 60*60*24;
            redisUtil.set(String.format(RedisConstants.WECHAT_OUT_TRADE_NO,outTradeNo),0,expireTime);
            data.put("body", weChatPayParam.getBody());
            /** wxPay.unifiedOrder 这个方法中调用微信统一下单接口，并获取返回数据 */
            Map<String, String> respData = wxPay.unifiedOrder(data);
            //如果接口调用成功且下单成功
            if ("SUCCESS".equals(respData.get("return_code")) && "SUCCESS".equals(respData.get("result_code"))) {
                payParamData.put("appid", myWeChatPayConfig.getAppID());
                payParamData.put("partnerid", myWeChatPayConfig.getMchID());
                payParamData.put("prepayid", respData.get("prepay_id"));
                payParamData.put("package", "Sign=WXPay");
                payParamData.put("noncestr", respData.get("nonce_str"));
                payParamData.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                String sign = WXPayUtil.generateSignature(payParamData, myWeChatPayConfig.getKey()); //签名
                payParamData.put("out_trade_no", data.get("out_trade_no"));
                payParamData.put("sign", sign);
            }
            return payParamData;
        } catch (Exception e) {
            throw new CustomException("微信下单失败");
        }
    }

    @Override
    public Map<String, String> queryWeChatOrder(String out_trade_no) {
        try {
            //初始化微信支付对象
            WXPay wxPay = new WXPay(myWeChatPayConfig);
            Map<String, String> reqData = new HashMap<>();
            reqData.put("appid", myWeChatPayConfig.getAppID());
            reqData.put("mch_id", myWeChatPayConfig.getMchID());
            //商户订单号
            reqData.put("out_trade_no", out_trade_no);
            reqData.put("nonce_str", WXPayUtil.generateNonceStr());
            String sign = WXPayUtil.generateSignature(reqData, myWeChatPayConfig.getKey()); //签名
            reqData.put("sign", sign);
            Map<String, String> respData = wxPay.orderQuery(reqData);
            return respData;
        } catch (Exception e) {
            throw new CustomException("查询微信订单失败");
        }
    }

    @Override
    public Map<String, String> unifiedOrderByApplet(WeChatUnifiedOrderDto unifiedOrderDto) {
        return null;
    }

    @Override
    public Map<String, String> refund(WeChatRefundDto refundDto) {
        try {
            //初始化微信支付对象
            WXPay wxPay = new WXPay(myWeChatPayConfig);
            Map<String, String> reqData = new HashMap<>();
            //微信订单号｜商户订单号二选一
            if (StringUtils.isNotBlank(refundDto.getTransaction_id())) {
                reqData.put("transaction_id", refundDto.getTransaction_id());
            }
            if (StringUtils.isNotBlank(refundDto.getOut_trade_no())) {
                reqData.put("out_trade_no", refundDto.getOut_trade_no());
            }
            reqData.put("appid", myWeChatPayConfig.getAppID());
            reqData.put("mch_id", myWeChatPayConfig.getMchID());
            reqData.put("nonce_str", WXPayUtil.generateNonceStr());
            reqData.put("out_refund_no", refundDto.getOut_refund_no());
            reqData.put("total_fee", Objects.toString(refundDto.getTotal_fee()));
            reqData.put("refund_fee", Objects.toString(refundDto.getRefund_fee()));
            reqData.put("sign", WXPayUtil.generateSignature(reqData, myWeChatPayConfig.getKey()));
            Map<String, String> refundData = wxPay.refund(reqData);
            return refundData;
        } catch (Exception e) {
            throw new CustomException("退款失败");
        }
    }

    @Override
    public boolean isSignatureValid(Map<String, String> requestData) {
        try {
            return WXPayUtil.isSignatureValid(requestData, myWeChatPayConfig.getKey());
        } catch (Exception e) {
            throw new CustomException("签名验证失败");
        }
    }
}

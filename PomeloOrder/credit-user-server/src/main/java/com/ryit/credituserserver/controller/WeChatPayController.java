package com.ryit.credituserserver.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.WeChatOrderQueryDto;
import com.ryit.commons.entity.dto.WeChatRefundDto;
import com.ryit.commons.entity.dto.WeChatUnifiedOrderDto;
import com.ryit.commons.util.RedisUtil;
import com.ryit.credituserserver.service.CreditBillService;
import com.ryit.credituserserver.service.IWeChatPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付接口
 *
 * @author samphin
 * @since 2019-9-20 14:58:25
 */
@RestController
@RequestMapping("/weChatPay")
@Slf4j
public class WeChatPayController extends BaseController {

    @Autowired
    private CreditBillService creditBillService;

    @Autowired
    private IWeChatPayService weChatPayService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 统一下单
     *
     * @author samphin
     * @date 2019-9-20 13:40:21
     */
    @PostMapping(value = "/unifiedOrder")
    public AjaxResult unifiedOrder(@RequestBody WeChatUnifiedOrderDto unifiedorderDto) {
        Map<String, String> payParamData = this.weChatPayService.unifiedOrderByApp(unifiedorderDto);
        return AjaxResult.success(payParamData);
    }

    /**
     * 申请退款
     *
     * @author samphin
     * @date 2019-9-20 11:58:12
     */
    @PostMapping(value = "/refund")
    public AjaxResult refund(@RequestBody WeChatRefundDto refundDto) {
        return AjaxResult.success(weChatPayService.refund(refundDto));
    }

    /**
     * 微信支付成功主动回调方法（方法不能带有任何参数）
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/anon/notify",produces = MediaType.APPLICATION_JSON_VALUE)
    public String payNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("==============微信支付回调===========");
        Map<String,String> return_data = new HashMap<>();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        //转换微信响应数据
        String weChatRespData = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = WXPayUtil.xmlToMap(weChatRespData);
        outSteam.close();
        inStream.close();
        //校验签名
        boolean isSign = this.weChatPayService.isSignatureValid(params);
        if (!isSign) {
            log.info("===============支付失败==============");
            // 支付失败
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            // 支付失败
            return WXPayUtil.mapToXml(return_data);
        } else {
            log.info("===============付款成功==============");
            // ------------------------------
            // 处理业务开始
            // ------------------------------
            // 此处处理订单状态，结合自己的订单数据完成订单状态的更新
            // ------------------------------
            String out_trade_no = params.get("out_trade_no");
            if (redisUtil.hasKey(String.format(RedisConstants.WECHAT_OUT_TRADE_NO, out_trade_no))) {
                redisUtil.set(String.format(RedisConstants.WECHAT_OUT_TRADE_NO, out_trade_no),1);
            }
            return_data.put("return_code", "SUCCESS");
            return_data.put("return_msg", "OK");
            return WXPayUtil.mapToXml(return_data);
        }
    }

    /**
     * 通过商户订单号查询订单是否支付成功1：成功；0:失败
     * @return
     */
    @GetMapping("/outTradeNoFlag")
    public AjaxResult queryOutTradeNoFlag(@RequestParam String outTradeNo){
        if (redisUtil.hasKey(String.format(RedisConstants.WECHAT_OUT_TRADE_NO, outTradeNo))) {
            Integer outTradeNoFlag = Integer.parseInt(redisUtil.get(String.format(RedisConstants.WECHAT_OUT_TRADE_NO, outTradeNo)));
            return AjaxResult.success(outTradeNoFlag);
        }
        return AjaxResult.error();
    }

    /**
     * 让APP支付成功后，主动调用我们自己的方法做用户余额更新,并返回支付结果
     *
     * @author samphin
     * @date 2019-9-22 09:13:26
     */
    @PostMapping("/rechargeCoin")
    public AjaxResult rechargeCoin(@RequestBody WeChatOrderQueryDto weChatOrderQueryDto, HttpServletRequest request){
        //如果从微信平台查询到该订单，则更新用户余额
        Map<String,String> resultData = new HashMap<>();
        //订单支付成功更新用户余额
        if ("0".equals(weChatOrderQueryDto.getErrCode())) {
            //查询微信订单是否存在
            Map<String, String> respData = weChatPayService.queryWeChatOrder(weChatOrderQueryDto.getOut_trade_no());
            if("SUCCESS".equals(respData.get("return_code"))&&"SUCCESS".equals(respData.get("result_code"))){
                //获取用户ID
                Long account = getUserId(request);
                //更新用户余额
                creditBillService.rechargeCoin(account, weChatOrderQueryDto.getRechargeId());
                resultData.put("message",respData.get("trade_state_desc"));
                resultData.put("code",respData.get("result_code"));
                String total_fee = respData.get("total_fee");
                double money = Double.valueOf(total_fee) / 100;
                resultData.put("description","柚子抢单-充值："+money+"元");
                return AjaxResult.success(resultData);
            }
        }
        return AjaxResult.error("用户余额变更失败，支付失败");
    }
}

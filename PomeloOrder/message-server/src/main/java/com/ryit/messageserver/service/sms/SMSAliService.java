package com.ryit.messageserver.service.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 刘修
 * @date: 2019/8/27 15:04
 * Description:短信发送
 */
@RestController
@RequestMapping(value = "/smsAli")
public class SMSAliService {
    private Logger log = LoggerFactory.getLogger(SMSAliService.class);


    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${sms.aliyun.SignName}")
    private String SignName;
    @Value("${sms.aliyun.TemplateCode}")
    private String TemplateCode;

    @Value("${sms.length}")
    private Integer codeLength;

    @Value("${sms.time}")
    private Long codeTime;

    @Autowired
    private RedisUtil redisUtil;

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/anon/sendSMS")
    public AjaxResult sendAliSMS(@RequestParam("phone") String phone) {
        try {
            String code = StringUtil.getRandomCode(codeLength);
            DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", "default");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", SignName);
            request.putQueryParameter("TemplateCode", TemplateCode);
            request.putQueryParameter("TemplateParam", "{\"code\":" + code + "}");
            CommonResponse response = client.getCommonResponse(request);
            Map<String, Object> map = JSON.parseObject(response.getData(), new TypeReference<Map<String, Object>>() {
            });
            if (!"OK".equals(map.get("Message"))) {
                return AjaxResult.error(0, "请求验证码失败！");
            }
            String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
            redisUtil.set(codeKey, code, codeTime);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求验证码错误！", e);
            return AjaxResult.error(0, "请求验证码失败！");
        }

    }
}

package com.ryit.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 短信服务
 *
 * @author: samphin
 * @since: 2019-11-2 16:28:09
 */
@Api(value = "SMSAliController",tags = "短信服务")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/sms")
public class SMSAliController {

    private Logger log = LoggerFactory.getLogger(SMSAliController.class);

    @Value("${aliyun.id}")
    private String id;
    @Value("${aliyun.secret}")
    private String secret;

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


    @ApiOperation(value = "获取手机验证码", httpMethod = "GET")
    @GetMapping("/{phone}")
    public ResponseData queryPhoneCode(@PathVariable("phone") String phone) {
        try {
            String code = StringUtil.getRandomCode(codeLength);
            DefaultProfile profile = DefaultProfile.getProfile("default", id, secret);
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
                return ResponseData.failure(0, "请求验证码失败！");
            }
            String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
            redisUtil.set(codeKey, code, codeTime);
            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求验证码错误！", e);
            return ResponseData.failure(0, "请求验证码失败！");
        }
    }
}

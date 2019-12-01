package com.ryit.messageserver.service.sms;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : 刘修
 * @Date : 2019/9/16 14:14
 */
@RestController
@RequestMapping(value = "/smsSC")
public class SMSSCService {

    @Value("${smsChinese.uid}")
    private String uid;
    @Value("${smsChinese.key}")
    private String key;

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
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
            //在头文件中设置转码
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
            NameValuePair[] data = {new NameValuePair("Uid", uid), new NameValuePair("Key", key), new NameValuePair("smsMob", phone), new NameValuePair("smsText", "您的验证码" + code + "该验证码5分钟内有效，请勿泄露于他人！如非本人操作，请忽略本短信！")};
            post.setRequestBody(data);

            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:" + statusCode);
//            for (Header h : headers) {
//                System.out.println(h.toString());
//            }
//            String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
            //打印返回消息状态
//            System.out.println(result);
            post.releaseConnection();
            String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
            redisUtil.set(codeKey, code, codeTime);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

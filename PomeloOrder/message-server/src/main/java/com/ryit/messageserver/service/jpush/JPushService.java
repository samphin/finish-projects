package com.ryit.messageserver.service.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.CreditMessageDto;
import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.messageserver.service.checktor.PushMessageDtoChecktor;
import com.ryit.messageserver.service.feign.CreditMessageFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: whsoso
 * @description: 极光推送
 * @author: Mr.Lu
 * @create: 2019-08-06 17:29
 **/
@RestController
@RequestMapping("/jpush")
public class JPushService {

    @Value("${jpush.credit.masterSecret}")
    private String creditMasterSecret;

    @Value("${jpush.credit.appKey}")
    private String creditAppKey;

    @Value("${jpush.wallet.masterSecret}")
    private String walletMasterSecret;

    @Value("${jpush.wallet.appKey}")
    private String walletAppKey;

    @Resource
    private CreditMessageFeignClient creditMessageFeignClient;

    /**
     * 极光推送
     *
     * @param pushMessageDto 消息模版类
     */
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/push")
    public void push(@RequestBody PushMessageDto pushMessageDto) {
        PushMessageDtoChecktor.check(pushMessageDto);
        String appType = pushMessageDto.getAppType();

        List users = pushMessageDto.getUsers();

        //实例一个jackson序列化对象
        ObjectMapper mapper = new ObjectMapper();

        //创建推送对象
        JPushClient jpushClient = generatorJPushClient(appType);
        //消息数据
        List<CreditMessageDto> messageDtoList = new ArrayList<>();
        try {
            for (Object user : users) {
                String phone = null;
                long userId = 0L;
                if("credit".equals(appType)){
                    CreditUser creditUser = mapper.convertValue(user, CreditUser.class);
                    phone = creditUser.getPhone();
                    userId = creditUser.getId();
                }else{
                    WalletUser walletUser = mapper.convertValue(user, WalletUser.class);
                    phone = walletUser.getPhone();
                    userId = walletUser.getId();
                }
                //汇总消息
                CreditMessageDto messageDto = new CreditMessageDto();
                //组装推送消息对象
                PushPayload payload = buildPushObjectAndroidIosAliasAlert(phone, pushMessageDto.getContent(), pushMessageDto.getTitle());
                //我们消息保存成功，再通过极光推送消息给对应用户手机上
                jpushClient.sendPush(payload);
                messageDto.setUserId(userId);
                messageDto.setTitle(pushMessageDto.getTitle());
                messageDto.setContent(pushMessageDto.getContent());
                messageDto.setPushTime(new Date());
                messageDtoList.add(messageDto);
            }
        } catch (APIConnectionException e) {
            throw new CustomException(e.getMessage(), e);
        } catch (APIRequestException e) {
            throw new CustomException(e.getMessage(), e);
        }
        //保存消息到业务库
        AjaxResult respData = creditMessageFeignClient.saveMessage(messageDtoList);
        int code = respData.getCode();
        String msg = respData.getMsg();
        if (code == 0) {
            throw new CustomException(msg);
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param phone
     * @param alert
     * @return
     */
    public static PushPayload buildPushObjectAndroidIosAliasAlert(String phone, String alert, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(phone))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .setTitle(title)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .addExtra("from", title)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(false)
                        //消息在JPush服务器的失效时间（测试使用参数）
                        .setTimeToLive(90)
                        .build())
                .build();
    }

    /**
     * 根据APP类型生成对应推送对象
     *
     * @param appType
     * @return
     */
    private JPushClient generatorJPushClient(String appType) {
        JPushClient jpushClient = null;
        if ("credit".equals(appType)) {
            jpushClient = new JPushClient(creditMasterSecret, creditAppKey, null, ClientConfig.getInstance());
        } else if ("wallet".equals(appType)) {
            jpushClient = new JPushClient(walletMasterSecret, walletAppKey, null, ClientConfig.getInstance());
        }
        return jpushClient;
    }

    /**
     * 根据APP类型生成app名称
     *
     * @param appType
     * @return
     */
    private String getAppName(String appType) {
        return "credit".equals(appType) ? "柚子抢单" : "七一钱包";
    }
}

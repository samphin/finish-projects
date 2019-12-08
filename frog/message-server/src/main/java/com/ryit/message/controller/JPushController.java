package com.ryit.message.controller;

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
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 极光推送
 * @author: samphin
 * @create: 2019-11-7 10:11:47
 **/
@Api(value = "JPushController", tags = "极光消息推送服务")
@RestController
@RequestMapping(BaseUrlConstants.BASE_API_PREFIX + "/jpush")
public class JPushController {

    @Value("${jpush.credit.masterSecret}")
    private String creditMasterSecret;

    @Value("${jpush.credit.appKey}")
    private String creditAppKey;

    /**
     * 以手机号作为别名推送
     *
     * @author samphin
     * @since 2019-9-17 15:09:45
     */
    @ApiOperation(value = "根据别名进行消息推送")
    @PostMapping("/push/aliasList")
    public void pushByAliasList(@RequestBody PushMessageDto pushMessageDto) {
        List<String> aliasList = pushMessageDto.getAliasList();
        if (CollectionUtils.isEmpty(aliasList)) {
            throw new CustomException("推送的手机号不能为空");
        }

        if (StringUtils.isBlank(pushMessageDto.getTargetApp())) {
            throw new CustomException("APP类型不能为空");
        }

        JPushClient jpushClient = new JPushClient(creditMasterSecret, creditAppKey, null, ClientConfig.getInstance());

        for (String alias : aliasList) {
            try {
                PushPayload payload = buildPushObjectAndroidIosAliasAlert(alias,
                        pushMessageDto.getTitle(), pushMessageDto.getContent());
                jpushClient.sendPush(payload);
            } catch (APIConnectionException e) {
                continue;
            } catch (APIRequestException e) {
                continue;
            }
        }
    }

    /**
     * 以手机号作为别名推送
     *
     * @author samphin
     * @since 2019-9-17 15:09:45
     */
    @PostMapping("/push/alias")
    public void pushByAlias(@RequestBody PushMessageDto pushMessageDto) {
        if (StringUtils.isEmpty(pushMessageDto.getAlias())) {
            throw new CustomException("推送的手机号不能为空");
        }

        if (StringUtils.isBlank(pushMessageDto.getTargetApp())) {
            throw new CustomException("APP类型不能为空");
        }

        try {
            JPushClient jpushClient = new JPushClient(creditMasterSecret, creditAppKey, null, ClientConfig.getInstance());
            PushPayload payload = buildPushObjectAndroidIosAliasAlert(pushMessageDto.getAlias(),
                    pushMessageDto.getTitle(), pushMessageDto.getContent());
            jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            throw new CustomException(e.getMessage(), e);
        } catch (APIRequestException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param alias 以别名推送
     * @param alert 弹出消息内容
     * @return
     */
    public static PushPayload buildPushObjectAndroidIosAliasAlert(String alias, String title, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
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
}

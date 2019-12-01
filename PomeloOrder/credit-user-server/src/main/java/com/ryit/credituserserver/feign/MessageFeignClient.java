package com.ryit.credituserserver.feign;

import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.entity.pojo.CreditUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "message-server")
public interface MessageFeignClient {

    /**
     * 极光推送消息
     *
     * @param pushMessageDto 消息模版对象
     */
    @PostMapping("/jpush/push")
    void push(@RequestBody PushMessageDto pushMessageDto);
}

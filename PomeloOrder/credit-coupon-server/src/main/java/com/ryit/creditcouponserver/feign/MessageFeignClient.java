package com.ryit.creditcouponserver.feign;

import com.ryit.commons.entity.dto.PushMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

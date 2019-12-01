package com.ryit.credithelpserver.feign.hystrix;

import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credithelpserver.feign.MessageFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: zhangweixun
 * @Date: 2019/10/18 0018下午 4:31
 */
@Component
public class MessageFeignHystrix implements MessageFeignClient {

    private Logger log = LoggerFactory.getLogger(MessageFeignHystrix.class);

    @Override
    public void push(PushMessageDto pushMessageDto) {
        log.error("极光推送消息服务中断");
        throw new CustomException("极光推送消息服务中断");
    }
}

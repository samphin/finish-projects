package com.ryit.messageserver.service.feign;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.CreditMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户模块消息
 *
 * @author samphin
 * @date 2019-9-30 12:10:11
 */
@FeignClient(value = "credit-user-server")
public interface CreditMessageFeignClient {

    @PostMapping("/creditMessage/save")
    AjaxResult saveMessage(@RequestBody List<CreditMessageDto> messageDtoList);
}

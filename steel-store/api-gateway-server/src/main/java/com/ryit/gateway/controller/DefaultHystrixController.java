package com.ryit.gateway.controller;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.enums.SystemErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认降级处理
 *
 * @author samphin
 * @since 2019-11-13 10:33:19
 */
@RestController
@Slf4j
public class DefaultHystrixController {

    @RequestMapping("/default_fallback")
    public ResponseData defaultFallback(@RequestParam("module") String module) {
        log.info("服务熔断操作...");
        ResponseData data = new ResponseData();
        data.setCode(SystemErrorEnum.HYSTRIX_FALLBACK_ERROR.getErrorCode());
        data.setMsg(module + SystemErrorEnum.HYSTRIX_FALLBACK_ERROR.getErrorMsg());
        return data;
    }
}

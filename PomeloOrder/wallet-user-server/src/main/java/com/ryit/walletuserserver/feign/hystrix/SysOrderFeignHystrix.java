package com.ryit.walletuserserver.feign.hystrix;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletuserserver.feign.SysOrderFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: zhangweixun
 * @Date: 2019/9/18 0018上午 10:13
 */
@Component
public class SysOrderFeignHystrix implements SysOrderFeignClient {

    private Logger log = LoggerFactory.getLogger(SysOrderFeignHystrix.class);

    @Override
    public AjaxResult insertSysOrder(SysOrderDto sysOrder) {
        log.error("提交订单时服务中断");
        throw new CustomException("提交订单时服务中断");
    }
}

package com.ryit.order.feign.impl;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.entity.vo.BusiCompanyVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.order.feign.ISysUserFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户服务熔断类
 *
 * @author samphin
 * @since 2019-11-25 10:09:47
 */
@Slf4j
public class SysUserFeignClientImpl implements FallbackFactory<ISysUserFeignClient> {

    @Override
    public ISysUserFeignClient create(Throwable cause) {
        log.error("用户服务熔断...", cause);
        return new ISysUserFeignClient() {

            @Override
            public ResponseData<BusiCompanyVo> queryCompanyInfoByUserId(Integer userId) {
                return ResponseData.failure(SystemErrorEnum.QUERY_USER_COMPANY_ERROR);
            }
        };
    }
}

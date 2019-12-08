package com.ryit.users.feign.impl;

import com.google.common.collect.Lists;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.entity.vo.BusiGoodsBrandVo;
import com.ryit.users.feign.IBusiGoodsFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 商品服务熔断处理类
 *
 * @author samphin
 * @since 2019-11-25 10:01:41
 */
@Slf4j
public class BusiGoodsFeignClientImpl implements FallbackFactory<IBusiGoodsFeignClient> {

    @Override
    public IBusiGoodsFeignClient create(Throwable cause) {
        log.error("商品服务熔断处理...", cause);
        return new IBusiGoodsFeignClient() {
            @Override
            public ResponseData<List<BusiGoodsBrandVo>> queryGoodsBrandList() {
                return ResponseData.failure().setData(Lists.newArrayList());
            }
        };
    }
}

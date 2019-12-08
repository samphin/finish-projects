package com.ryit.order.feign.impl;

import com.google.common.collect.Lists;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.entity.dto.BusiGoodsSkuDto;
import com.ryit.commons.entity.vo.BusiGoodsSkuInfoVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.order.feign.IBusiGoodsFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

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
            public ResponseData<List<BusiGoodsSkuInfoVo>> saveBatch(List<BusiGoodsSkuDto> dtos) {
                return ResponseData.failure(SystemErrorEnum.GOODS_SKU_SAVE_ERROR).setData(Lists.newArrayList());
            }

            @Override
            public ResponseData deleteTrolleyGoods(Set<Long> goodsIdList) {
                return ResponseData.failure(SystemErrorEnum.GOODS_TROLLEY_EMPTY_ERROR).setData(Lists.newArrayList());
            }
        };
    }
}

package com.ryit.order.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiGoodsSkuDto;
import com.ryit.commons.entity.vo.BusiGoodsSkuInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * 商品规格Feign接口
 *
 * @author samphin
 * @since 2019-10-29 15:22:43
 */
@FeignClient(name = "goods-server")
public interface IBusiGoodsFeignClient {

    /**
     * 批量保存商品规格
     *
     * @param dtos
     * @return
     */
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods_sku/batch", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<List<BusiGoodsSkuInfoVo>> saveBatch(@RequestBody List<BusiGoodsSkuDto> dtos);

    /**
     * 清空购物车指定商品信息
     */
    @DeleteMapping(BaseUrlConstants.BASE_API_PREFIX + "/goods_trolley/goods")
    ResponseData deleteTrolleyGoods(@RequestParam Set<Long> goodsIdList);
}

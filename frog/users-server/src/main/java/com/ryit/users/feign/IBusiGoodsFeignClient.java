package com.ryit.users.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.vo.BusiGoodsBrandVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "goods-server")
public interface IBusiGoodsFeignClient {

    /**
     * 查询系统商品类别列表
     *
     * @return
     */
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/goods_brands")
    ResponseData<List<BusiGoodsBrandVo>> queryGoodsBrandList();
}

package com.ryit.order.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.vo.BusiCompanyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户模块客户端
 *
 * @author samphin
 * @since 2019-11-11 19:49:05
 */
@FeignClient(name = "users-server")
public interface ISysUserFeignClient {

    /**
     * 根据用户ID查询公司详情
     *
     * @return
     */
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/companies/{userId}")
    ResponseData<BusiCompanyVo> queryCompanyInfoByUserId(@PathVariable("userId") Integer userId);
}

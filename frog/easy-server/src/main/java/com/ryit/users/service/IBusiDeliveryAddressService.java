package com.ryit.users.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.BusiDeliveryAddressDto;
import com.ryit.commons.entity.vo.BusiDeliveryAddressVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IBusiDeliveryAddressService extends IBaseService<Long, BusiDeliveryAddressDto, BusiDeliveryAddressVo> {

    /**
     * 查询我的收货地址
     *
     * @param request
     * @return
     */
    List<BusiDeliveryAddressVo> queryMyAddress(HttpServletRequest request);

}
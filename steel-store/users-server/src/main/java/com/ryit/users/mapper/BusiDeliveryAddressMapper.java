package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.BusiDeliveryAddress;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BusiDeliveryAddressMapper extends Mapper<BusiDeliveryAddress> {

    Integer resetOtherAddress(@Param("userId")Integer userId,@Param("id")Long id);
}
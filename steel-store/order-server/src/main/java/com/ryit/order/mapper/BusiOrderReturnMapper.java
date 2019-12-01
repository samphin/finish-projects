package com.ryit.order.mapper;

import com.ryit.commons.entity.pojo.BusiOrderReturn;
import tk.mybatis.mapper.common.Mapper;

public interface BusiOrderReturnMapper extends Mapper<BusiOrderReturn> {
    Integer insertBusIOrderRefundInfo(BusiOrderReturn busiOrderReturn);
}

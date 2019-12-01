package com.ryit.creditcouponserver.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.CreditInviteDto;
import com.ryit.commons.entity.vo.CreditInviteVo;
import org.apache.ibatis.annotations.Param;

/**
 * 邀请方案业务接口
 *
 * @author samphin
 * @date 2019-9-3 12:02:57
 */
public interface ICreditInviteService extends IBaseService<Long, CreditInviteDto, CreditInviteVo> {

    /**
     * 校验邀请人数据是否存在
     * @param num
     * @author samphin
     * @date 2019-9-9 15:05:12
     */
    boolean validateNum(Integer num);
}

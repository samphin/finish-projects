package com.ryit.credituserserver.dao;

import com.ryit.commons.entity.pojo.CreditMessageStandard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditMessageStandardMapper {

    /**
     * 保存订单偏好设置信息
     * @param record
     * @author samphin
     * @date 2019-9-3 09:15:07
     * @return
     */
    int insertSelective(CreditMessageStandard record);

    /**
     * 通过用户ID，查询订单偏好设置信息
     *
     * @param userId
     * @author samphin
     * @date 2019-9-3 09:14:29
     */
    CreditMessageStandard selectMessageStandard(@Param("userId") Long userId);

}
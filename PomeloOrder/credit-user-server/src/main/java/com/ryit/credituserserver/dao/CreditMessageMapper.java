package com.ryit.credituserserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.CreditMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CreditMessageMapper extends BaseMapper<Long,CreditMessage> {

    /**
     * 查询历史消息
     * @param userId
     * @author samphin
     * @date 2019-9-1 10:06:26
     * @return
     */
    List<CreditMessage> selectHistoryMessage(@Param("userId")Long userId);

    /**
     * 修改消息读状态
     * @param params
     * @author samphin
     * @date 2019-9-30 10:11:33
     * @return
     */
    Integer updateMessageReadStatus(Map<String,Object> params);
}
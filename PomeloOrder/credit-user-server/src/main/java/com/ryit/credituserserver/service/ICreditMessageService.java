package com.ryit.credituserserver.service;

import com.ryit.commons.entity.dto.CreditMessageDto;
import com.ryit.commons.entity.dto.CreditMessageStandardDto;
import com.ryit.commons.entity.pojo.CreditMessage;
import com.ryit.commons.entity.pojo.CreditMessageStandard;
import com.ryit.commons.entity.vo.CreditMessageStandardVo;
import com.ryit.commons.entity.vo.CreditMessageVo;

import java.util.List;

public interface ICreditMessageService {

    /**
     * 保存订单偏好信息
     *
     * @author samphin
     * @date 2019-9-1 10:12:12
     */
    Boolean saveBillBehavior(Long userId,CreditMessageStandardDto creditMessageStandardDto);

    /**
     * 查询订单偏好
     * @author samphin
     * @date 2019-9-1 11:56:47
     * @return
     */
    CreditMessageStandardVo queryBillBehavior(Long userId);

    /**
     * 保存消息
     * @author samphin
     * @date 2019-9-30 10:07:29
     * @return
     */
    Boolean saveMessage(List<CreditMessageDto> messageDtoList);

    /**
     * 修改消息状态
     * @author samphin
     * @date 2019-9-30 10:07:36
     * @return
     */
    Boolean updateMessageReadStatus(Long userId,List<Long> ids);

    /**
     * 查询我的历史消息
     *
     * @author samphin
     * @date 2019-9-1 09:53:19
     */
    List<CreditMessageVo> queryHistoryMessage(Long userId);
}

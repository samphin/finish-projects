package com.ryit.messageserver.service.checktor;

import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 极光推送消息校验器
 * @author samphin
 * @date 2019-9-30 12:12:30
 */
public class PushMessageDtoChecktor {

    public static void check(PushMessageDto dto){

        if(null == dto){
            throw new CustomException("消息不能为空");
        }

        if(CollectionUtils.isEmpty(dto.getUsers())){
            throw new CustomException("目标用户为空");
        }

        if(StringUtils.isEmpty(dto.getAppType())){
            throw new CustomException("目标APP不能为空");
        }

    }
}

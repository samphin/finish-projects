package com.ryit.credithelpserver.checktor;

import com.ryit.commons.entity.dto.CreditNotesDto;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang.StringUtils;

/**
 * 留言校验器
 * @author samphin
 * @date 2019-8-29 22:08:00
 */
public class CreditNotesDtoChecktor {

    public static void check(CreditNotesDto dto){

        if(null == dto){
            throw new CustomException("留言信息不能为空");
        }

        if(StringUtils.isBlank(dto.getNote())){
            throw new CustomException("留言描述信息不能为空");
        }

        if(StringUtils.isBlank(dto.getPhone())){
            throw new CustomException("留言人手机号不能为空");
        }

        if(StringUtils.isBlank(dto.getEmail())){
            throw new CustomException("留言人E-mail不能为空");
        }
    }
}

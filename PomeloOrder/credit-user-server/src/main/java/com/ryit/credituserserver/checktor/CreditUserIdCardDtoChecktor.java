package com.ryit.credituserserver.checktor;

import com.ryit.commons.entity.dto.CreditUserIdCardDto;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户身份证校验器
 * @author samphin
 * @date 2019-10-15 09:29:53
 */
public class CreditUserIdCardDtoChecktor {

    public static void check(CreditUserIdCardDto dto){
        if(null == dto){
            throw new CustomException("身份证信息不能为空");
        }

        if(StringUtils.isEmpty(dto.getRealName())){
            throw new CustomException("身份证真实姓名不能为空");
        }

        if(StringUtils.isEmpty(dto.getRealCode())){
            throw new CustomException("身份证号码不能为空");
        }

        if(StringUtils.isEmpty(dto.getActiveLife())){
            throw new CustomException("身份证有效期不能为空");
        }

        if(StringUtils.isEmpty(dto.getIssuingUnit())){
            throw new CustomException("身份证签发机关不能为空");
        }
    }
}

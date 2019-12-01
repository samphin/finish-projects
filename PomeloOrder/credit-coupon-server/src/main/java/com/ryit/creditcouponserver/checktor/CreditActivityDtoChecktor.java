package com.ryit.creditcouponserver.checktor;

import com.ryit.commons.entity.dto.CreditActivityDto;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang3.StringUtils;

/**
 * 活动信息校验器
 *
 * @author samphin
 * @date 2019-9-30 14:10:20
 */
public class CreditActivityDtoChecktor {

    public static void check(CreditActivityDto dto) {
        if (null == dto) {
            throw new CustomException("活动信息不能为空");
        }

        if (StringUtils.isEmpty(dto.getTitle())) {
            throw new CustomException("活动标题不能为空");
        }

        if (StringUtils.isEmpty(dto.getImageUrl())) {
            throw new CustomException("活动图片不能为空");
        }
    }
}

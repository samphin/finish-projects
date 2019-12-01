package com.ryit.orderserver.checktor;

import com.ryit.commons.entity.dto.SysApplicationDto;
import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.pojo.SysOrderAptitude;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * APP版本信息校验器
 * @author: samphin
 * @Date: 2019-10-11 17:03:38
 */
public class SysApplicationDtoChecktor {

    public static void check(SysApplicationDto dto) {

        if (null == dto) {
            throw new CustomException("APP版本信息不能为空");
        }

        if(StringUtils.isEmpty(dto.getCode())){
            throw new CustomException("APP编码不能为空");
        }

        if(StringUtils.isEmpty(dto.getDownloadPath())){
            throw new CustomException("APP下载地址不能为空");
        }

        if(StringUtils.isEmpty(dto.getVersion())){
            throw new CustomException("APP版本号不能为空");
        }

        if(null == dto.getUpdateFlag()){
            throw new CustomException("APP是否强制更新状态不能为空");
        }
    }
}

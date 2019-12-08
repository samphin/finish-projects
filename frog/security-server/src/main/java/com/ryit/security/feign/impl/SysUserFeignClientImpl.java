package com.ryit.security.feign.impl;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.entity.dto.SysUserDto;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.security.feign.ISysUserFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 用户服务熔断类
 *
 * @author samphin
 * @since 2019-11-25 10:09:47
 */
@Slf4j
public class SysUserFeignClientImpl implements FallbackFactory<ISysUserFeignClient> {

    @Override
    public ISysUserFeignClient create(Throwable cause) {
        log.error("用户服务熔断...", cause);
        return new ISysUserFeignClient() {

            @Override
            public ResponseData<SysUser> queryLoginInfoByPhone(String phone) {
                return ResponseData.failure(SystemErrorEnum.QUERY_LOGIN_INFO_ERROR);
            }

            @Override
            public ResponseData<Boolean> existTelephone(String phone) {
                return ResponseData.failure(SystemErrorEnum.EXIST_TELEPHONE_ERROR);
            }

            @Override
            public ResponseData<Boolean> register(SysUserDto dto) {
                return ResponseData.failure(SystemErrorEnum.USER_REGISTER_ERROR);
            }

            @Override
            public ResponseData<List<SysSimpleRoleVo>> queryUserHaveRoles(Integer id) {
                return ResponseData.failure(SystemErrorEnum.QUERY_USER_ROLES_ERROR);
            }
        };
    }
}

package com.ryit.security.feign;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.SysUserDto;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户模块客户端
 *
 * @author samphin
 * @since 2019-11-11 19:49:05
 */
@FeignClient(name = "users-server")
public interface ISysUserFeignClient {

    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return SysUser
     * @author samphin
     * @since 2019-10-16 17:43:37
     */
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/users/phone/{phone}")
    ResponseData<SysUser> queryLoginInfoByPhone(@PathVariable("phone") String phone);

    /**
     * 校验手机号是否存在
     *
     * @param phone
     * @return Integer
     * @author samphin
     * @since 2019-10-16 17:46:28
     */
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/users/exist/{phone}")
    ResponseData<Boolean> existTelephone(@PathVariable("phone") String phone);

    /**
     * 用户注册
     *
     * @param dto
     * @return
     */
    @PostMapping(BaseUrlConstants.BASE_API_PREFIX + "/users")
    ResponseData<Boolean> register(@RequestBody SysUserDto dto);

    /**
     * 查询用户角色
     */
    @GetMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/{id}/roles")
    ResponseData<List<SysSimpleRoleVo>> queryUserHaveRoles(@PathVariable Integer id);
}

package com.ryit.users.controller;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.SysBuyerListVo;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import com.ryit.commons.entity.vo.SysUserVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.RedisUtil;
import com.ryit.users.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "SysUserController", tags = "用户管理接口")
@RestController
@RequestMapping
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    //============================Feign调用接口 Start====================================

    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return SysUser
     * @author samphin
     * @since 2019-10-16 17:43:37
     */
    @ApiIgnore
    @ApiOperation(value = "用户手机号查询登录信息", httpMethod = "GET")
    @ApiImplicitParam(paramType = "path", name = "phone", value = "用户手机号", required = true, dataType = "String")
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/users/phone/{phone}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<SysUser> queryLoginInfoByPhone(@PathVariable("phone") String phone) {
        SysUser sysUser = sysUserService.queryLoginInfoByPhone(phone);
        return ResponseData.success().setData(sysUser);
    }

    /**
     * 校验手机号是否存在
     *
     * @param phone
     * @return Integer
     * @author samphin
     * @since 2019-10-16 17:46:28
     */
    @ApiIgnore
    @ApiOperation(value = "用户手机号查询登录信息", httpMethod = "GET")
    @ApiImplicitParam(paramType = "path", name = "phone", value = "用户手机号", required = true, dataType = "String")
    @GetMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/users/exist/{phone}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Boolean> existTelephone(@PathVariable("phone") String phone) {
        boolean bl = sysUserService.existTelephone(phone);
        //如果不存在，则true
        if (!bl) {
            return ResponseData.success();
        } else {
            //如果存在，则提示已注册
            throw new CustomException(SystemErrorEnum.EXIST_TELEPHONE_ERROR);
        }
    }

    /**
     * 用户注册
     *
     * @author samphin
     * @since 2019-10-16 17:46:28
     */
    @ApiIgnore
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_API_PREFIX + "/users")
    public ResponseData<Boolean> register(@Validated @RequestBody SysUserDto dto) {
        boolean bl = sysUserService.register(dto);
        if (bl) {
            return ResponseData.success().setData(bl);
        } else {
            return ResponseData.failure();
        }
    }

    //=================================API接口 Start======================================

    //============================PC端管理后台接口 Start====================================

    @ApiOperation(value = "管理员新增用户信息", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users")
    public ResponseData save(@Validated @RequestBody SysUserDto dto) {
        boolean bl = sysUserService.insertSelective(dto);
        if (bl) {
            return ResponseData.success().setData(bl);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "修改用户信息", httpMethod = "PATCH", notes = "修改一条用户信息")
    @PatchMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX + "/users",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/users"
    })
    public ResponseData update(@Validated @RequestBody SysUserDto dto) {
        boolean bl = sysUserService.updateByIdSelective(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "查询我的信息", httpMethod = "GET", notes = "根据用户ID，查询用户详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "Integer")
    @GetMapping(BaseUrlConstants.BASE_API_PREFIX + "/users/{id}")
    public ResponseData queryMyInfo(@PathVariable Integer id) {
        SysUserVo vo = sysUserService.queryMyInfo(id);
        return ResponseData.success().setData(vo);
    }

    @ApiOperation(value = "查询咨询用户信息", httpMethod = "GET", notes = "查询咨询用户信息")
    @GetMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users")
    public ResponseData queryAdvisoryUserList(@ModelAttribute SysBuyerQueryDto queryDto) {
        List<SysBuyerListVo> voList = sysUserService.queryAdvisoryUserList(queryDto);
        return ResponseData.success().setData(voList);
    }

    /**
     * APP修改手机号
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "APP修改手机号", httpMethod = "PATCH")
    @PatchMapping(BaseUrlConstants.BASE_API_PREFIX + "/users/phone")
    public ResponseData updateMobilePhone(@Validated @RequestBody SysUserAppMobilePhoneDto dto, HttpServletRequest request) {
        validatePhoneCode(dto.getNewMobilePhone(), dto.getValidateCode());
        boolean bl = sysUserService.updateMobilePhone(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * APP修改密码
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "APP修改密码", httpMethod = "PATCH")
    @PatchMapping(BaseUrlConstants.BASE_API_PREFIX + "/users/password")
    public ResponseData updatePasswordForApp(@Validated @RequestBody SysUserAppPasswordDto dto, HttpServletRequest request) {
        validatePhoneCode(dto.getMobilePhone(), dto.getValidateCode());
        boolean bl = sysUserService.updatePasswordForApp(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 校验手机号
     *
     * @param mobilePone   手机号
     * @param validateCode 手机验证码
     * @return
     */
    private void validatePhoneCode(String mobilePone, String validateCode) {
        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, mobilePone);
        //判断验证码是否已过期
        if (!redisUtil.hasKey(codeKey)) {
            throw new CustomException(SystemErrorEnum.GET_VALIDATE_CODE);
        }

        //从redis获取未过期的验证码
        String code = redisUtil.get(codeKey);
        if (!code.equals(validateCode)) {
            throw new CustomException(SystemErrorEnum.VALIDATE_CODE_ERROR);
        }
    }

    @ApiOperation(value = "修改用户密码", httpMethod = "PATCH", notes = "修改用户密码")
    @PatchMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/password")
    public ResponseData updatePassword(@Validated @RequestBody SysUserPasswordDto dto, HttpServletRequest request) {
        boolean bl = sysUserService.updatePassword(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "注销用户信息", httpMethod = "PATCH", notes = "修改用户状态")
    @PatchMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/status")
    public ResponseData logon(@Validated @RequestBody SysUserStateDto dto) {
        boolean bl = sysUserService.updateState(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "彻底删除用户信息", httpMethod = "DELETE", notes = "删除用户信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "Integer")
    @DeleteMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        boolean bl = sysUserService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "分页查询用户信息", httpMethod = "POST", notes = "通过分页方式查询用户列表信息")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/page")
    public ResponseData queryPageList(@RequestBody BaseQueryDto<SysUserDto> queryDto) {
        PageBean<SysUserVo> pageList = sysUserService.queryPageList(queryDto);
        return ResponseData.success().setData(pageList);
    }

    @ApiOperation(value = "查询用户详情", httpMethod = "GET", notes = "根据用户ID，查询用户详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "Integer")
    @GetMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/{id}")
    public ResponseData queryOne(@PathVariable Integer id) {
        SysUserVo vo = sysUserService.queryById(id);
        return ResponseData.success().setData(vo);
    }

    @ApiOperation(value = "用户分配角色", httpMethod = "POST", notes = "根据用户ID，给用户分配一个或多个角色")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/roles")
    public ResponseData bindingsRoles(@Validated @RequestBody SysUserRolePkDto dto) {
        boolean bl = this.sysUserService.bindingRoles(dto);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation(value = "查询用户已拥有的角色信息", httpMethod = "GET")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "Integer")
    @GetMapping(value = BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/{id}/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<List<SysSimpleRoleVo>> queryUserHaveRoles(@PathVariable Integer id) {
        List<SysSimpleRoleVo> sysGroupRoleVoList = this.sysUserService.queryUserHaveRoles(id);
        return ResponseData.success().setData(sysGroupRoleVoList);
    }

    @ApiOperation(value = "查询用户未拥有的角色信息", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/other/roles")
    public ResponseData queryUserHaveNoRoles(@RequestBody SysUserRolePkQueryDto dto) {
        List<SysSimpleRoleVo> sysGroupRoleVoList = this.sysUserService.queryUserHaveNoRoles(dto);
        return ResponseData.success().setData(sysGroupRoleVoList);
    }

    /**
     * 查询所有买家用户信息
     *
     * @author samphin
     * @since 2019-11-25 16:31:57
     */
    @ApiOperation(value = "查询所有买家用户信息", httpMethod = "POST")
    @PostMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/users/buyers")
    public ResponseData queryBuyers(@RequestBody BaseQueryDto<SysBuyerQueryDto> queryDto) {
        PageBean<SysBuyerListVo> pageBean = this.sysUserService.queryBuyers(queryDto);
        return ResponseData.success().setData(pageBean);
    }
}

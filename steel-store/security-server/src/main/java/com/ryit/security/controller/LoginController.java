package com.ryit.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.constants.JwtConstant;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.entity.dto.LoginDto;
import com.ryit.commons.entity.dto.SysUserDto;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.LoginUserInfoVo;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.Md5Util;
import com.ryit.commons.util.RedisUtil;
import com.ryit.security.feign.ISysUserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统登录/登出
 *
 * @author : samphin
 * @since : 2019-10-15 18:55:44
 */
@Api(value = "LoginController", tags = "登录接口")
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private ISysUserFeignClient sysUserFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 刷新token时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    /**
     * 系统登录（管理员、超管、卖家登录）
     *
     * @param requestData
     * @param response
     * @author samphin
     * @since 2019-10-16 15:54:23
     */
    @ApiOperation(value = "系统登录", httpMethod = "POST")
    @PostMapping(value={
            BaseUrlConstants.BASE_API_PREFIX + "/login",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/login"
    })
    public ResponseData login(@Validated(LoginDto.Login.class) @RequestBody LoginDto requestData, HttpServletRequest request, HttpServletResponse response) {
        //手机号
        String phone = requestData.getTelephone();
        //密码
        String password = requestData.getPassword();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new CustomException(SystemErrorEnum.ERROR_PARAM);
        }

        //读取redis缓存用户信息
        ObjectMapper objectMapper = new ObjectMapper();
        Object redisUser = redisUtil.getObject(String.format(RedisConstants.PREFIX_USER_INFO, phone));
        SysUser user = objectMapper.convertValue(redisUser, SysUser.class);

        //如果redis用户不存在，则从数据库查询用户
        if (null == user) {

            ResponseData<SysUser> responseData = sysUserFeignClient.queryLoginInfoByPhone(phone);
            if (responseData.getCode() != HttpStatus.SC_OK) {
                throw new CustomException(responseData.getMsg());
            }

            user = responseData.getData();
        }

        password = Md5Util.encrypt(password);
        if (null == user) {
            throw new CustomException(SystemErrorEnum.USER_NO_EXIST);
        } else if (!user.getPassword().equals(password)) {
            throw new CustomException(SystemErrorEnum.LOGIN_PASSWORD_ERROR);
        }

        //查询用户角色权限信息
        ResponseData<List<SysSimpleRoleVo>> responseData = sysUserFeignClient.queryUserHaveRoles(user.getId());
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        List<SysSimpleRoleVo> roleVoList = responseData.getData();

        List<String> roles = roleVoList.stream().map(SysSimpleRoleVo::getCode).collect(Collectors.toList());

        //获取请求地址
        String requestUrl = request.getRequestURI();
        //如果包含api，说明是APP【买家】登录，否则PC端【管理员、买家、超管角色】登录
        if(requestUrl.contains("/api")){
            boolean isBuyer = roles.stream().anyMatch(role->"BUYER".equals(role));
            if(!isBuyer){
                throw new CustomException(SystemErrorEnum.ILLEGAL_LOGIN_ERROR);
            }
        }else{
            boolean isAdmin = roles.stream().anyMatch(role->"SUP_ADMIN".equals(role) || "ADMIN".equals(role) || "SELLER".equals(role));
            if(!isAdmin){
                throw new CustomException(SystemErrorEnum.ILLEGAL_LOGIN_ERROR);
            }
        }

        return getLoginUserInfo(user, response);
    }

    /**
     * 注册用户
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "APP用户注册", httpMethod = "POST")
    @PostMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/register")
    public ResponseData register(@Validated(LoginDto.Register.class) @RequestBody LoginDto registerDto, HttpServletRequest request) {
        //手机号
        String phone = registerDto.getTelephone();
        //验证码
        String validateCode = registerDto.getValidateCode();
        //验证码为空
        if (StringUtils.isEmpty(validateCode)) {
            throw new CustomException(SystemErrorEnum.GET_VALIDATE_CODE);
        }

        //校验手机中是否已注册
        ResponseData<Boolean> responseData = sysUserFeignClient.existTelephone(phone);
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }

        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
        //判断验证码是否已过期
        if (!redisUtil.hasKey(codeKey)) {
            throw new CustomException(SystemErrorEnum.GET_VALIDATE_CODE);
        }

        //从redis获取未过期的验证码
        String code = redisUtil.get(codeKey);
        if (code.equals(validateCode)) {
            SysUserDto dto = new SysUserDto();
            dto.setUsername(phone);
            dto.setMobilePhone(phone);
            dto.setPassword(registerDto.getPassword());
            ResponseData<Boolean> registerResult = sysUserFeignClient.register(dto);
            if (registerResult.getCode() != HttpStatus.SC_OK) {
                throw new CustomException(registerResult.getMsg());
            }
            boolean saveFlag = registerResult.getData();
            if (saveFlag) {
                redisUtil.remove(codeKey);
            }
            return ResponseData.success();
        } else {
            throw new CustomException(SystemErrorEnum.VALIDATE_CODE_ERROR);
        }
    }

    /**
     * 登出
     *
     * @author chenyongfeng
     * @since 2019-10-16 15:53:57
     */
    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX + "/logout",
            BaseUrlConstants.BASE_ADMIN_PREFIX + "/logout"
    })
    public ResponseData logout(HttpServletRequest request) {
        //获取jwtToken
        String jwtToken = request.getHeader(JwtConstant.AUTHORIZATION);
        if (StringUtils.isNotBlank(jwtToken)) {
            String account = JwtUtil.getClaim(jwtToken, JwtConstant.ACCOUNT);
            if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account))) {
                redisUtil.remove(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, account));
            }
            if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_USER_INFO, account))) {
                //清除登录用户信息
                redisUtil.remove(String.format(RedisConstants.PREFIX_USER_INFO, account));
            }
        }
        return ResponseData.success();
    }

    /**
     * 获取登录的用户信息
     *
     * @param user
     * @param response
     * @author samphin
     * @since 2019-9-16 15:10:26
     */
    private ResponseData getLoginUserInfo(SysUser user, HttpServletResponse response) {
        String mobilePhone = user.getMobilePhone();
        // 清除可能存在的shiro权限信息缓存
        if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_SHIRO_CACHE, mobilePhone))) {
            redisUtil.remove(String.format(RedisConstants.PREFIX_SHIRO_CACHE, mobilePhone));
        }

        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        redisUtil.set(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, mobilePhone), currentTimeMillis,
                Integer.parseInt(refreshTokenExpireTime));
        //设置用户缓存
        redisUtil.set(String.format(RedisConstants.PREFIX_USER_INFO, mobilePhone), user,
                Integer.parseInt(refreshTokenExpireTime));

        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(mobilePhone, currentTimeMillis);
        response.setHeader(JwtConstant.AUTHORIZATION, token);
        response.setHeader("Access-Control-Expose-Headers", JwtConstant.AUTHORIZATION);
        //返回登录信息
        LoginUserInfoVo loginUserInfoVo = new LoginUserInfoVo().buildVo(user);
        return ResponseData.success().setData(loginUserInfoVo);
    }

    /**
     * 未授权跳转地址
     *
     * @return
     * @author samphin
     * @since 2019-10-17 17:36:25
     */
    @ApiIgnore
    @GetMapping(BaseUrlConstants.BASE_ADMIN_PREFIX + "/unauth")
    public ResponseData unauth() {
        return ResponseData.failure(SystemErrorEnum.PLEASE_LOGIN);
    }
}

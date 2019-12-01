package com.ryit.creditapigateway.controller;


import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.constant.JwtConstant;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.WeChatUserInfoDto;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.util.EndecryptUtil;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.creditapigateway.feign.CreditUserFeignClient;
import com.ryit.creditapigateway.feign.OrderFeignClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : 刘修
 * @Date : 2019/8/19 12:12
 */
@RestController
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private CreditUserFeignClient creditUserFeignClient;

    @Resource
    private OrderFeignClient orderFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody JSONObject requestData, HttpServletResponse response) {
        try {
            String phone = requestData.getString("phone");
            String password = requestData.getString("password");
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
                return AjaxResult.error(0, "参数错误");
            }
            CreditUser user = (CreditUser) redisUtil.getObject(String.format(RedisConstants.PREFIX_CREDIT_USER, phone));
            if (null == user) {
                user = creditUserFeignClient.getCreditUserByPhone(phone);
            }
            if (null == user || user.getPassword() == null) {
                return AjaxResult.error(0, "用户不存在");
            } else if (!user.getPassword().equals(EndecryptUtil.encrytMd5(password))) {
                return AjaxResult.error(0, "密码错误");
            }
            return getLoginUserInfo(user, response);
        } catch (AuthenticationException e) {
            return AjaxResult.error(0, e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error(0, "未知错误");
        }
    }

    @PostMapping("/register")
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult register(@RequestBody JSONObject requestData, HttpServletRequest request) {
        String phone = requestData.getString("phone");
        String password = requestData.getString("password");
        String phoneCode = requestData.getString("phoneCode");
        String inviteCode = requestData.getString("inviteCode");
        if (StringUtil.isEmpty(phone)) {
            return AjaxResult.error(0, "未获取到手机号码");
        }
        if (StringUtil.isEmpty(password)) {
            return AjaxResult.error(0, "未获取到密码");
        }
        if (!creditUserFeignClient.checkPhone(phone)) {
            return AjaxResult.error(0, "手机号码已注册,请直接登录！");
        }
        password = EndecryptUtil.encrytMd5(password);
        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
        if (redisUtil.hasKey(codeKey) && !StringUtil.isEmpty(phoneCode)) {
            String code = redisUtil.get(codeKey);
            if (StringUtil.isNotEmpty(code)) {
                if (code.equals(phoneCode)) {
                    CreditUser user = new CreditUser();
                    user.setUserName(phone);
                    user.setPhone(phone);
                    user.setPassword(password);
                    if (!StringUtil.isEmpty(inviteCode)) {
                        user.setInviteCode(inviteCode);
                    }
                    Integer res = creditUserFeignClient.registerUser(user);
                    if (res > 0) {
                        redisUtil.remove(codeKey);
                        return AjaxResult.success();
                    }
                }
                return AjaxResult.error(0, "验证码错误");
            }
        }
        return AjaxResult.error(0, "请先获取验证码");
    }

    @GetMapping("/logout")
    public AjaxResult logout() {
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        if (StringUtil.isNotBlank(token)) {
            String account = JwtUtil.getClaim(token, JwtConstant.ACCOUNT);
            if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_CREDIT_API_REFRESH_TOKEN, account))) {
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_API_REFRESH_TOKEN, account));
            }
            if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_CREDIT_USER, account))) {
                //清除登录用户信息
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, account));
            }
        }
        return AjaxResult.success();
    }


    /******************************************第三方登录（微信）*************************************/
    /**
     * 通过openID查询用户手机号、密码
     *
     * @param requestData
     * @param response
     * @return
     */
    @PostMapping("/weChatLogin")
    public AjaxResult weChatLogin(@RequestBody JSONObject requestData, HttpServletResponse response) {
        String openId = requestData.getString("openId");
        CreditUser user = creditUserFeignClient.queryCreditUserByOpenId(openId);
        if (null != user) {
            return getLoginUserInfo(user, response);
        } else {
            return AjaxResult.error(101, "用户不存在，请注册");
        }
    }

    /**
     * 微信用户注册
     *
     * @param weChatUserInfoDto
     * @param response
     * @return
     */
    @PostMapping("/weChatRegister")
    public AjaxResult weChatLogin(@RequestBody WeChatUserInfoDto weChatUserInfoDto, HttpServletResponse response) {
        //获取手机号码
        String phone = weChatUserInfoDto.getPhone();
        if (StringUtil.isEmpty(phone)) {
            return AjaxResult.error(0, "请填写手机号");
        }

        //获取密码
        String password = weChatUserInfoDto.getPassword();
        if (StringUtil.isEmpty(password)) {
            return AjaxResult.error(0, "请填写密码");
        }

        //手机验证码
        String phoneCode = weChatUserInfoDto.getPhoneCode();
        if (StringUtil.isEmpty(phone)) {
            return AjaxResult.error(0, "请填写验证码");
        }

        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
        if (redisUtil.hasKey(codeKey) && !StringUtil.isEmpty(phoneCode)) {
            String code = (String) redisUtil.get(codeKey);
            if (StringUtil.isNotEmpty(code)) {
                if (code.equals(phoneCode)) {
                    //加密MD5加密
                    password = EndecryptUtil.encrytMd5(password);
                    //通过手机号查询抢单用户
                    AjaxResult respData = creditUserFeignClient.queryUserByPhone(phone);
                    if (0 == respData.getCode()) {
                        throw new CustomException(respData.getMsg());
                    }
                    //获取查询数据
                    Object data = respData.getData();
                    //如果手机号已存在抢单用户直接更新，然后登录
                    if (null != data) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        CreditUser creditUser = objectMapper.convertValue(data, CreditUser.class);
                        creditUser.setAvatar(weChatUserInfoDto.getHeadImgUrl());
                        creditUser.setPassword(password);
                        creditUser.setOpenId(weChatUserInfoDto.getOpenId());
                        AjaxResult result = creditUserFeignClient.updateUser(creditUser);
                        if (0 == result.getCode()) {
                            throw new CustomException(result.getMsg());
                        }
                        return getLoginUserInfo(creditUser, response);
                    } else {//当前手机号从未注册，则直接新增
                        CreditUser user = new CreditUser();
                        user.setUserName(weChatUserInfoDto.getNickname());
                        user.setPhone(phone);
                        user.setPassword(password);
                        user.setAvatar(weChatUserInfoDto.getHeadImgUrl());
                        user.setOpenId(weChatUserInfoDto.getOpenId());
                        //将微信性别信息，转换成我们自己的性别编码
                        switch (weChatUserInfoDto.getSex()) {
                            case 1://男
                                user.setSex(1);
                                break;
                            case 2://女
                                user.setSex(0);
                                break;
                            case 0://未知
                                user.setSex(2);
                                break;
                        }
                        //注册用户
                        Integer res = creditUserFeignClient.registerUser(user);
                        //注册成功
                        if (res > 0) {
                            redisUtil.remove(codeKey);
                            //重新通过openID进行登录
                            user = creditUserFeignClient.queryCreditUserByOpenId(weChatUserInfoDto.getOpenId());
                            return getLoginUserInfo(user, response);
                        }
                    }
                }
                return AjaxResult.error(0, "验证码错误");
            }
        }
        return AjaxResult.error(0, "请先获取验证码");
    }

    /**
     * 获取登录的用户信息
     *
     * @param user
     * @param response
     * @author samphin
     * @date 2019-9-16 15:10:26
     */
    private AjaxResult getLoginUserInfo(CreditUser user, HttpServletResponse response) {
        String phone = user.getPhone();
        // 清除可能存在的shiro权限信息缓存
        if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_SHIRO_CACHE, phone))) {
            redisUtil.remove(String.format(RedisConstants.PREFIX_SHIRO_CACHE, phone));
        }

        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        redisUtil.set(String.format(RedisConstants.PREFIX_CREDIT_API_REFRESH_TOKEN, phone), currentTimeMillis,
                Integer.parseInt(refreshTokenExpireTime));
        //设置用户缓存
        redisUtil.set(String.format(RedisConstants.PREFIX_CREDIT_USER, phone), user,
                Integer.parseInt(refreshTokenExpireTime));

        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(phone, currentTimeMillis);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("pomeloFlag", orderFeignClient.getPomeloFlag());
        user.setPassword(null);
        return AjaxResult.success(user);
    }
}

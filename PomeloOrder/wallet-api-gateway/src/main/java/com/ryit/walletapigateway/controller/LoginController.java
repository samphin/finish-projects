package com.ryit.walletapigateway.controller;


import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.constant.JwtConstant;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.WeChatUserInfoDto;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.WalletUserVo;
import com.ryit.commons.util.*;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletapigateway.feign.SysOrderFeignClient;
import com.ryit.walletapigateway.feign.WalletUserFeignClient;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * @author : 刘修
 * @Date : 2019/8/19 12:12
 */
@RestController
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private WalletUserFeignClient walletUserFeignClient;

    @Resource
    private SysOrderFeignClient sysOrderFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        try {
            String phone = jsonObject.getString("phone");
            String password = jsonObject.getString("password");
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
                return AjaxResult.error(0, "参数错误");
            }
            WalletUser user = new ObjectMapper().convertValue(
                    redisUtil.getObject(String.format(RedisConstants.PREFIX_WALLET_USER, phone)), WalletUser.class);
            if (null == user) {
                user = walletUserFeignClient.getWalletUserByPhone(phone);
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
    public AjaxResult register(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        String phoneCode = jsonObject.getString("phoneCode");
        if (StringUtil.isEmpty(phone)) {
            return AjaxResult.error(0, "未获取到手机号码");
        }
        if (StringUtil.isEmpty(password)) {
            return AjaxResult.error(0, "未获取到密码");
        }
        WalletUser u = walletUserFeignClient.queryUserByPhone(phone);
        if (null != u) {
            return AjaxResult.error(0, "手机号码已注册,请直接登录！");
        }
        password = EndecryptUtil.encrytMd5(password);
        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
        if (redisUtil.hasKey(codeKey) && !StringUtil.isEmpty(phoneCode)) {
            String code = redisUtil.get(codeKey);
            if (StringUtil.isNotEmpty(code)) {
                if (code.equals(phoneCode)) {
                    WalletUser user = new WalletUser();
                    user.setUserName(phone);
                    user.setPhone(phone);
                    user.setPassword(password);
                    Integer res = walletUserFeignClient.registerUser(user);
                    if (res > 0) {
                        redisUtil.remove(codeKey);
                        return AjaxResult.success("注册成功");
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
            if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_WALLET_API_REFRESH_TOKEN, account))) {
                redisUtil.remove(String.format(RedisConstants.PREFIX_WALLET_API_REFRESH_TOKEN, account));
            }
            if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_WALLET_USER, account))) {
                redisUtil.remove(String.format(RedisConstants.PREFIX_WALLET_USER, account));
            }
        }
        return AjaxResult.success();
    }

    /**
     * h5页面用户提交申请(h5页面无密码注册)
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/registration")
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult registration(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String phoneCode = jsonObject.getString("phoneCode");
        String sourceUrl = jsonObject.getString("sourceUrl");
        if (StringUtil.isEmpty(phoneCode)) {
            return AjaxResult.error(0, "未获取到手机号码");
        }
        if (StringUtil.isEmpty(phone)) {
            return AjaxResult.error(0, "未获取到手机验证码");
        }
        Integer orderCount = sysOrderFeignClient.queryOrderWithinDay(phone);
        if (orderCount > 0) {
            return AjaxResult.error(0, "您在24小时内提交过订单,请耐心等候");
        }
        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, phone);
        if (redisUtil.hasKey(codeKey) && !StringUtil.isEmpty(phoneCode)) {
            String code = redisUtil.get(codeKey);
            if (StringUtil.isNotEmpty(code)) {
                if (code.equals(phoneCode)) {
                    //用户信息在数据库不存在 为用户添加数据
                    WalletUser user = walletUserFeignClient.queryUserByPhone(phone);
                    if (null == user) {
                        user = new WalletUser();
                        user.setUserName(phone);
                        user.setPhone(phone);
                        user.setReleaseTime(new Date());
                        //用户来源编码 后续从字典表中信息补全
//                        user.setSourceCode("");
                        //用户来源名称 后续从字典表中信息补全
//                        user.setSourceName("");
                        //用户来源路径 后续从字典表中信息补全
                        user.setSourceUrl(sourceUrl);
                        AjaxResult ajaxResult = walletUserFeignClient.registerWalletUser(user);
                        if (ajaxResult.getCode().equals(0) || null == ajaxResult.getData()) {
                            return AjaxResult.error("操作失败");
                        }
                        user.setId(Long.valueOf(Objects.toString(ajaxResult.getData())));
                    }
                    WalletUserVo vo = new WalletUserVo();
                    BeanUtils.copyBeanProp(vo, user);
                    if (StringUtils.isBlank(vo.getCreditorIdcard())) {
                        vo.setIsAccreditation(false);
                    } else {
                        vo.setIsAccreditation(true);
                    }
                    vo.setPassword(null);
                    redisUtil.remove(codeKey);
                    return AjaxResult.success(vo);
                }
                return AjaxResult.error(0, "验证码错误");
            }
        }
        return AjaxResult.error(0, "验证码错误");
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
        AjaxResult ajax = walletUserFeignClient.queryByOpenId(openId);
        if (ajax.getCode().equals(0) || null == ajax.getData()) {
            return AjaxResult.error(ajax.getMsg());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        WalletUser user = objectMapper.convertValue(ajax.getData(), WalletUser.class);
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
                    WalletUser walletUser = walletUserFeignClient.queryUserByPhone(phone);
                    //用户openid存在时 直接返回登录信息
                    if (null != walletUser && StringUtils.isNotBlank(walletUser.getOpenId())) {
                        return getLoginUserInfo(walletUser, response);
                    }
                    //用户openId不存在时保存用户的openid及其他信息
                    if (null != walletUser && StringUtils.isBlank(walletUser.getOpenId())) {
                        walletUser.setAvatar(weChatUserInfoDto.getHeadImgUrl());
                        walletUser.setPassword(password);
                        walletUser.setOpenId(weChatUserInfoDto.getOpenId());
                        AjaxResult ajaxResult = walletUserFeignClient.updateUserOpenId(walletUser);
                        if (0 == ajaxResult.getCode()) {
                            throw new CustomException(ajaxResult.getMsg());
                        }
                        return getLoginUserInfo(walletUser, response);
                    }
                    //用户不存在时 注册新用户
                    WalletUser user = new WalletUser();
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
                    AjaxResult ajaxResult = walletUserFeignClient.registerWalletUser(user);
                    if (ajaxResult.getCode().equals(0) || null == ajaxResult.getData()) {
                        return AjaxResult.error("操作失败");
                    }
                    //注册成功
                    redisUtil.remove(codeKey);
                    //重新通过openID进行登录
                    AjaxResult ajax = walletUserFeignClient.queryByOpenId(weChatUserInfoDto.getOpenId());
                    if (ajax.getCode().equals(0) || null == ajax.getData()) {
                        return AjaxResult.error("操作失败");
                    }
                    user = (WalletUser) ajax.getData();
                    return getLoginUserInfo(user, response);
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
    private AjaxResult getLoginUserInfo(WalletUser user, HttpServletResponse response) {
        String phone = user.getPhone();
        // 清除可能存在的shiro权限信息缓存
        if (redisUtil.hasKey(String.format(RedisConstants.PREFIX_SHIRO_CACHE, phone))) {
            redisUtil.remove(String.format(RedisConstants.PREFIX_SHIRO_CACHE, phone));
        }

        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        redisUtil.set(String.format(RedisConstants.PREFIX_WALLET_API_REFRESH_TOKEN, phone), currentTimeMillis,
                Integer.parseInt(refreshTokenExpireTime));
        //设置用户缓存
        redisUtil.set(String.format(RedisConstants.PREFIX_WALLET_USER, phone), user,
                Integer.parseInt(refreshTokenExpireTime));

        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(phone, currentTimeMillis);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        user.setPassword(null);
        return AjaxResult.success(user);
    }
}

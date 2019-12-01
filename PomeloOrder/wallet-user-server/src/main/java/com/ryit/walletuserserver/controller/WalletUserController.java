package com.ryit.walletuserserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.dto.WalletUserDto;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.WalletUserVo;
import com.ryit.commons.util.BeanUtils;
import com.ryit.commons.util.EndecryptUtil;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import com.ryit.walletuserserver.checktor.WalletUserChecktor;
import com.ryit.walletuserserver.service.WalletUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 柚子抢单用户管理
 *
 * @author liuxiu
 */
@RestController
@RequestMapping("/walletUser")
public class WalletUserController extends BaseController {
    private Logger log = LoggerFactory.getLogger(WalletUserController.class);


    @Autowired
    private WalletUserService walletUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @GetMapping("/getWalletUserByPhone")
    public WalletUser getWalletUserByPhone(String phone) {
        return walletUserService.getWalletUserByPhone(phone);
    }

    @PostMapping("/registerUser")
    public Integer registerUser(@RequestBody WalletUser user) {
        return walletUserService.registerUser(user);
    }

    /**
     * h5用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/registerWalletUser")
    public AjaxResult registerWalletUser(@RequestBody WalletUser user) {
        Long id = walletUserService.registerWalletUser(user);
        if (null == id) {
            return AjaxResult.error();
        }
        return AjaxResult.success(id);
    }


    @GetMapping("/checkPhone")
    public Boolean checkPhone(String phone) {
        return walletUserService.checkPhone(phone);
    }

    /**
     * 查询用户个人信息
     *
     * @param request
     * @return
     */
    @GetMapping("/userInfo")
    public AjaxResult queryUserInfo(HttpServletRequest request) {
        try {
            Long id = getUserId(request);
            WalletUserVo walletUserVo = walletUserService.queryUserInfo(id);
            if (walletUserVo != null) {
                return AjaxResult.success(walletUserVo);
            }
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
        }
        return AjaxResult.error("获取用户信息失败");
    }

    /**
     * 查询用户的身份证和真实姓名(用于提交订单用户信息认证时的信息回显)
     *
     * @param request
     * @return
     */
    @GetMapping("/userIdCardAndName")
    public AjaxResult queryIdCardAndName(HttpServletRequest request) {
        Long id = getUserId(request);
        WalletUser walletUser = walletUserService.queryIdCardAndName(id);
        return AjaxResult.success(walletUser);
    }

    /**
     * 修改是否接受消息推送状态
     *
     * @param request
     * @return
     */
    @PostMapping("/updateMessageFlag")
    public AjaxResult updateMessageFlag(HttpServletRequest request) {
        Long id = getUserId(request);
        Integer messageFlag = walletUserService.updateMessageFlag(id);
        if (messageFlag == null) {
            return AjaxResult.error("修改是否接受消息推送失败");
        } else {
            return AjaxResult.success(messageFlag);
        }
    }

    /**
     * 修改用户名或头像
     *
     * @param request
     * @param walletUser
     * @return
     */
    @PostMapping("/updateUserNameOrAvatar")
    public AjaxResult updateUserNameOrAvatar(HttpServletRequest request, @RequestBody WalletUser walletUser) {
        Long id = getUserId(request);
        walletUser.setId(id);
        walletUserService.updateUserNameOrAvatar(walletUser);
        WalletUser userCache = walletUserService.getUserSimple(id);
        redisUtil.set(String.format(RedisConstants.PREFIX_WALLET_USER, userCache.getPhone()), userCache, Integer.parseInt(refreshTokenExpireTime));
        return AjaxResult.success();
    }

    /**
     * 用户修改账户密码
     *
     * @param user
     * @return
     */
    @PostMapping("/anon/updatePassword")
    public AjaxResult updatePassword(@RequestBody WalletUserDto user) {
        String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, user.getPhone());
        String code = redisUtil.get(codeKey);
        if (StringUtils.isBlank(user.getPhone())) {
            return AjaxResult.error("电话号码不能为空");
        }
        if (StringUtil.isEmpty(code) || !code.equals(user.getPhoneCode())) {
            return AjaxResult.error("验证码错误");
        }
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword()));
        WalletUser walletUser = new WalletUser();
        BeanUtils.copyBeanProp(walletUser, user);
        Boolean flag = walletUserService.updatePassword(walletUser);
        if (flag) {
            redisUtil.remove(codeKey);
            WalletUser userCache = walletUserService.getWalletUserByPhone(walletUser.getPhone());
            redisUtil.set(String.format(RedisConstants.PREFIX_WALLET_USER, userCache.getPhone()), userCache, Integer.parseInt(refreshTokenExpireTime));
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码失败");
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestBody WalletUser user, HttpServletRequest request) {
        Long account = getUserId(request);
        user.setId(account);
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword()));
        if (walletUserService.updatePassword(user)) {
            WalletUser userCache = walletUserService.getUserSimple(account);
            redisUtil.set(String.format(RedisConstants.PREFIX_WALLET_USER, userCache.getPhone()), userCache, Integer.parseInt(refreshTokenExpireTime));
            return AjaxResult.success();
        }
        return AjaxResult.error(0, "重置密码错误");
    }


    /**
     * 用户信息认证
     *
     * @param sysOrder
     * @return
     */
    @PostMapping("/certifiedInformation")
    public AjaxResult certifiedInformation(@RequestBody SysOrderDto sysOrder, HttpServletRequest request) {
        Boolean flag = false;
        try {
            Long account = getUserId(request);
            sysOrder.setWalletUserId(account);
            sysOrder.getUserInfo().setId(account);
            flag = walletUserService.certifiedInformation(sysOrder);
        } catch (Exception e) {
            return AjaxResult.error("用户提交认证信息失败");
        }
        if (flag) {
            return AjaxResult.success();
        }
        return AjaxResult.error("用户提交认证信息失败");
    }

    /**
     * 用户电话查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/anon/getUser/{phone}")
    public WalletUser queryUserByPhone(@PathVariable("phone") String phone) {
        return walletUserService.getUserInformation(phone);
    }

    @GetMapping("/walletUser/queryWalletUser/{id}")
    public WalletUser queryWalletUserById(@PathVariable("id") Long id) {
        return walletUserService.getUserById(id);
    }


    /**
     * 用于已用手机号注册用户使用微信登录
     *
     * @param walletUser
     * @return
     */
    @PutMapping("/updateUserOpenId")
    public AjaxResult updateUserOpenId(@RequestBody WalletUser walletUser) {
        boolean flag = walletUserService.updateUserOpenId(walletUser);
        if (flag) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }


    /**
     * 提供给前端使用的用户提交接口
     *
     * @param walletUser
     * @return
     */
    @PostMapping("/anon/submissionWalletUser")
    public AjaxResult subWalletUser(@RequestBody WalletUser walletUser) {
        Long id = walletUserService.insertWalletUser(walletUser);
        if (id == null) {
            return AjaxResult.error("用户保存失败");
        }
        return AjaxResult.success(id);
    }


    /**
     * 订单服务请求-->保存用户基本信息
     *
     * @param walletUser
     * @return
     */
    @PostMapping("/anon/submissionUser")
    public AjaxResult insertWalletUser(@RequestBody WalletUser walletUser) {
        WalletUserChecktor.checkWalletUser(walletUser);
        return AjaxResult.success(walletUserService.insertWalletUser(walletUser));
    }

    /**
     * openId查询用户信息
     *
     * @param openId
     * @return
     */
    @GetMapping("/anon/getUserByOpenId/{openId}")
    public AjaxResult queryByOpenId(@PathVariable("openId") String openId) {
        return AjaxResult.success(walletUserService.queryByOpenId(openId));
    }

    /**
     * 根据用户电话修改身份证号
     *
     * @param walletUser
     * @return
     */
    @PutMapping("/updateIdCard")
    public AjaxResult updateIdCardByPhone(@RequestBody WalletUser walletUser) {
        boolean bl = walletUserService.updateIdCardByPhone(walletUser);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询订单贷款人信息
     *
     * @author samphin
     * @date 2019-10-14 14:35:13
     */
    @GetMapping("/queryOrderWalletor/{orderId}")
    public AjaxResult queryOrderWalletor(@PathVariable("orderId") Long orderId) {
        WalletUser walletUser = walletUserService.queryOrderWalletor(orderId);
        return AjaxResult.success(walletUser);
    }

    /**********************后台接口**********************/
    /**
     * 查询用户列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/walletUser")
    public AjaxResult queryAdminList(@ModelAttribute WalletUserDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletUserVo> result = walletUserService.queryAdminList(dto);
        return AjaxResult.success(getPageData(result));
    }


    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/walletUser/{id}")
    public AjaxResult getUserById(@PathVariable("id") Long id) {
        return AjaxResult.success(walletUserService.queryAdminBy(id));
    }

    /**
     * 后台添加管理员
     *
     * @param walletUserDto
     * @return
     */
    @PostMapping("/admin/walletUser")
    public AjaxResult insertAdmin(@RequestBody WalletUserDto walletUserDto) {
        Boolean flag = walletUserService.insertAdmin(walletUserDto);
        if (flag) {
            return AjaxResult.success(flag);
        }
        return AjaxResult.error();
    }

    /**
     * 后台修改管理员
     *
     * @param walletUserDto
     * @return
     */
    @PutMapping("/admin/walletUser")
    public AjaxResult updateAdmin(@RequestBody WalletUserDto walletUserDto) {
        Boolean flag = walletUserService.updateAdmin(walletUserDto);
        if (flag) {
            return AjaxResult.success(flag);
        }
        return AjaxResult.error();
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/walletUser/{id}")
    public AjaxResult deleteAdmin(@PathVariable("id") Long id) {
        Boolean flag = walletUserService.deleteAdmin(id);
        if (flag) {
            return AjaxResult.success(flag);
        }
        return AjaxResult.error();
    }

    /**
     * 管理员查询用户列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/user")
    public AjaxResult queryUserList(@ModelAttribute WalletUserDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<WalletUser> result = walletUserService.queryUserList(dto);
        return AjaxResult.success(getPageData(result));
    }
}

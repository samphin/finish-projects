package com.ryit.credituserserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.CreditNotAdminUserDto;
import com.ryit.commons.entity.dto.CreditUserDto;
import com.ryit.commons.entity.dto.CreditUserIdCardDto;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.pojo.CreditUserCard;
import com.ryit.commons.entity.vo.CreditNotAdminUserVo;
import com.ryit.commons.entity.vo.CreditUserInviteInfoVo;
import com.ryit.commons.entity.vo.CreditUserVo;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.StringUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.commons.web.exception.user.UserPasswordNotMatchException;
import com.ryit.credituserserver.checktor.CreditUserIdCardDtoChecktor;
import com.ryit.credituserserver.service.CreditUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 柚子抢单用户管理
 *
 * @author liuxiu
 */
@RestController
@RequestMapping("/creditUser")
@Slf4j
public class CreditUserController extends BaseController {

    @Autowired
    private CreditUserService creditUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @GetMapping("/getCreditUserByPhone")
    public CreditUser getCreditUserByPhone(String phone) {
        return creditUserService.getCreditUserByPhone(phone);
    }

    @PostMapping("/registerUser")
    public Integer registerUser(@RequestBody CreditUser user) {
        return creditUserService.registerUser(user);
    }

    /**
     * 更新用户信息
     *
     * @author samphin
     * @date 2019-10-14 16:52:15
     */
    @PutMapping("/updateUser")
    public AjaxResult updateUser(@RequestBody CreditUser user) {
        boolean bl = creditUserService.updateUserInfo(user);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 根据手机号查询抢单用户
     *
     * @author samphin
     * @date 2019-10-14 16:53:08
     */
    @GetMapping("/queryUserByPhone/{phone}")
    public AjaxResult queryUserByPhone(@PathVariable("phone") String phone) {
        CreditUser creditUser = creditUserService.queryUserByPhone(phone);
        return AjaxResult.success(creditUser);
    }


    /**
     * 新增用户并返回用户主键
     *
     * @author samphin
     * @date 2019-10-14 17:52:06
     */
    @PostMapping("/registerUserCallBack")
    public Long registerUserCallBack(@RequestBody CreditUser user) {
        return creditUserService.insertCreditUserCallBack(user);
    }

    /**
     * 查询退单次数
     *
     * @param id
     * @return
     */
    @GetMapping("/getBackOrderNum")
    public Integer getBackOrderNum(Long id) {
        return creditUserService.getBackOrderNum(id);
    }

    /**
     * 更新用户余额
     *
     * @param id
     * @param coin
     * @return
     */
    @PostMapping("/updateCreditUserCoin")
    public Integer updateCreditUserCoin(Long id, Double coin) {
        return creditUserService.updateCreditUserCoin(id, coin);
    }

    /**
     * 获取用户余额
     *
     * @param id
     * @return
     */
    @GetMapping("/getCreditUserCoin")
    public Double getCreditUserCoin(Long id) {
        try {
            return creditUserService.getCreditUserCoin(id);
        } catch (Exception e) {
            log.error("请求用户余额错误", e);
            return 0d;
        }
    }

    @GetMapping("/checkPhone")
    public Boolean checkPhone(String phone) {
        try {
            return creditUserService.checkPhone(phone);
        } catch (Exception e) {
            log.error("校验手机号错误", e);
            return false;
        }
    }

    @PostMapping("/addBackOrderNum")
    public void addBackOrderNum(Long id) {
        try {
            creditUserService.addBackOrderNum(id);
        } catch (Exception e) {
            log.error("增加用户退单次数错误", e);
        }
    }

    /**
     * 将当前用户退单次数减1，并退还用户金额
     *
     * @author samphin
     * @date 2019-9-3 20:29:03
     */
    @PutMapping("/subtractBackOrderNum")
    public boolean subtractBackOrderNum(Long id, Double coin) {
        return creditUserService.subtractBackOrderNum(id, coin);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getCreditUser")
    public AjaxResult getCreditUser(HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            return AjaxResult.success(creditUserService.getCreditUser(account));
        } catch (Exception e) {
            log.error("请求用户信息错误", e);
            return AjaxResult.error(0, "请求用户信息错误");
        }
    }

    /**
     * 修改头像和昵称
     */
    @PostMapping("/midifyBaseInfo")
    public AjaxResult midifyBaseInfo(@RequestBody CreditUserDto user, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            user.setId(account);
            if (creditUserService.modifyBaseInfo(user) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(account);
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
//                redisUtil.set(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()), userCache, Integer.parseInt(refreshTokenExpireTime));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("更新用户信息错误", e);
        }
        return AjaxResult.error(0, "更新用户信息错误");
    }

    /**
     * 修改密码
     */
    @PostMapping("/midifyPassword")
    public AjaxResult midifyPassword(@RequestBody CreditUserDto user, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            user.setId(account);
            if (creditUserService.modifyPassword(user) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(account);
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
//                redisUtil.set(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()), userCache, Integer.parseInt(refreshTokenExpireTime));
                return AjaxResult.success();
            }
        } catch (UserPasswordNotMatchException upnme) {
            log.error("密码错误", upnme);
            return AjaxResult.error(0, "密码错误");
        } catch (Exception e) {
            log.error("修改密码错误", e);
        }
        return AjaxResult.error(0, "修改密码错误");
    }

    /**
     * 验证手机号码
     */
    @PostMapping("/anon/checkPhone")
    public AjaxResult checkPhone(@RequestBody CreditUserDto user, HttpServletRequest request) {
        try {
            String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, user.getPhone());
            if (!redisUtil.hasKey(codeKey) || StringUtil.isEmpty(user.getPhoneCode())) {
                return AjaxResult.error(0, "请先获取验证码");
            }
            String code = redisUtil.get(codeKey);
            if (StringUtil.isEmpty(code) || !code.equals(user.getPhoneCode())) {
                return AjaxResult.error(0, "验证码错误");
            }
            redisUtil.remove(codeKey);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("验证手机号码错误", e);
        }
        return AjaxResult.error(0, "验证手机号码错误");
    }

    /**
     * 修改手机号码
     */
    @PostMapping("/midifyPhone")
    public AjaxResult midifyPhone(@RequestBody CreditUserDto user, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            user.setId(account);
            String codeKey = String.format(RedisConstants.PHONE_CODE_KEY, user.getPhone());
            if (!redisUtil.hasKey(codeKey) || StringUtil.isEmpty(user.getPhoneCode())) {
                return AjaxResult.error(0, "请先获取验证码");
            }
            String code = redisUtil.get(codeKey);
            if (StringUtil.isEmpty(code) || !code.equals(user.getPhoneCode())) {
                return AjaxResult.error(0, "验证码错误");
            }
            if (creditUserService.modifyPhone(user) > 0) {
                redisUtil.remove(codeKey);
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, user.getOldPhone()));
                CreditUser userCache = creditUserService.getCreditUserForRedis(account);
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
//                redisUtil.set(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()), userCache, Integer.parseInt(refreshTokenExpireTime));
                return AjaxResult.success();
            }
        } catch (CustomException e) {
            log.error("修改手机号码错误", e);
            return AjaxResult.error(0, e.getMessage());
        } catch (Exception e) {
            log.error("修改手机号码错误", e);
        }
        return AjaxResult.error(0, "修改手机号码错误");
    }

    /**
     * 上传身份证
     */
    @PostMapping("/uploadIdentityImg")
    public AjaxResult uploadIdentityImg(MultipartFile frontImgFile, MultipartFile afterImgFile, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            if (null == frontImgFile || null == afterImgFile) {
                return AjaxResult.error(0, "上传失败");
            }
            BASE64Encoder encoder = new BASE64Encoder();
            CreditUserCard userCard = new CreditUserCard();
            userCard.setUserId(account);
            if (null != frontImgFile) {
                String frontImg = encoder.encode(frontImgFile.getBytes());
                userCard.setFrontImg(frontImg);
            }
            if (null != afterImgFile) {
                String afterImg = encoder.encode(afterImgFile.getBytes());
                userCard.setAfterImg(afterImg);
            }
            return AjaxResult.success(creditUserService.uploadIdentityImg(userCard));
        } catch (IOException e) {
            log.error("上传身份证错误", e);
            return AjaxResult.error(0, "上传身份证错误");
        } catch (Exception e) {
            log.error("上传身份证错误", e);
            return AjaxResult.error(0, "上传身份证错误");
        }
    }

    /**
     * 提交认证信息
     */
    @PostMapping("/commitAuthInfo")
    public AjaxResult commitAuthInfo(@RequestBody CreditUserDto user, HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            user.setId(account);
            return AjaxResult.success(creditUserService.commitAuthInfo(user));
        } catch (Exception e) {
            log.error("提交认证信息错误", e);
            return AjaxResult.error(0, "提交认证信息错误");
        }
    }


    /**
     * 查询认证信息
     */
    @GetMapping("/getAuthInfo")
    public AjaxResult getAuthInfo(HttpServletRequest request) {
        try {
            Long account = getUserId(request);
            return AjaxResult.success(creditUserService.getAuthInfo(account));
        } catch (Exception e) {
            log.error("查询认证信息错误", e);
            return AjaxResult.error(0, "查询认证信息错误");
        }
    }

    /**
     * 重置密码
     */
    @PostMapping("/anon/resetPassword")
    public AjaxResult resetPassword(@RequestBody CreditUserDto user) {
        try {
            if (creditUserService.reserPassword(user) == 1) {
                CreditUser userCache = creditUserService.getCreditUserByPhone(user.getPhone());
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("重置密码错误", e);
        }
        return AjaxResult.error(0, "重置密码错误");
    }

    /**
     * 根据openId查询用户手机号、密码
     *
     * @author samphin
     * @date 2019-9-16 13:38:15
     */
    @GetMapping("/queryCreditUserByOpenId")
    public CreditUser queryCreditUserByOpenId(@RequestParam("openId") String openId) {
        CreditUser creditUser = creditUserService.queryCreditUserByOpenId(openId);
        return creditUser;
    }

    /**
     * 查询用户列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/getCreditUserByPage")
    public AjaxResult getCreditUserByPage(@ModelAttribute CreditUserDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<CreditUserVo> result = creditUserService.getCreditUserByPage(dto);
        return AjaxResult.success(getNewPageData(result));
    }

    /**
     * 获取所有非管理员用户
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:49:07
     */
    @GetMapping("/queryAllNotAdminCreditUsers")
    public List<CreditUser> queryAllNotAdminCreditUsers() {
        return creditUserService.queryAllCreditUsers(0);
    }

    /**
     * 根据用户ID，查询用户信息
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:49:07
     */
    @GetMapping("/queryCreditUserListById")
    public List<CreditUser> queryCreditUserListById(@RequestParam("id") Long id) {
        return creditUserService.queryCreditUserById(id);
    }

    /**
     * 查询用户成功邀请
     *
     * @author samphin
     * @date 2019-10-9 16:08:08
     */
    @GetMapping("/queryUserInviteInfo")
    public AjaxResult queryUserInviteInfo(HttpServletRequest request) {
        Long account = getUserId(request);
        CreditUserInviteInfoVo vo = creditUserService.queryUserInviteInfo(account);
        return AjaxResult.success(vo);
    }


    //===============================PC端接口==========================================

    /**
     * 查询管理员列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/admin/getAdminByPage")
    public AjaxResult getAdminByPage(@ModelAttribute CreditUserDto dto) {
        startPage(dto.getPageNum(), dto.getPageSize(), true);
        List<CreditUserVo> result = creditUserService.getAdminList(dto);
        return AjaxResult.success(getNewPageData(result));
    }

    /**
     * 查询管理员列表非分页
     *
     * @return
     */
    @GetMapping("/admin/managers")
    public AjaxResult queryManagers(@ModelAttribute CreditUserDto dto) {
        List<CreditUserVo> result = creditUserService.getAdminList(dto);
        return AjaxResult.success(result);
    }

    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/getCreditUserInfo")
    public AjaxResult getCreditUserInfo(@RequestParam("id") Long id) {
        CreditUserVo result = creditUserService.getCreditUserInfo(id);
        return AjaxResult.success(result);
    }

    /**
     * 审核用户
     *
     * @param creditUser
     * @return
     */
    @PostMapping("/admin/verifyUser")
    public AjaxResult verifyUser(@RequestBody CreditUser creditUser) {
        try {
            if (creditUserService.updateAuthFlag(creditUser) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(creditUser.getId());
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("审核用户异常", e);
        }
        return AjaxResult.error();
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/deleteAdmin")
    public AjaxResult deleteAdmin(@RequestParam("id") Long id, HttpServletRequest request) {
        int num = 0;
        try {
            CreditUser userCache = creditUserService.getCreditUserForRedis(id);
            redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
            num = creditUserService.deleteAdmin(id, getUserId(request));
        } catch (Exception e) {
            log.error("删除管理员异常", e);
        }
        return toAjax(num);
    }

    /**
     * 添加管理员
     *
     * @param creditUserDto
     * @return
     */
    @PostMapping("/admin/addAdmin")
    public AjaxResult addAdmin(@RequestBody CreditUserDto creditUserDto) {
        int num = 0;
        try {
            num = creditUserService.addAdmin(creditUserDto);
        } catch (Exception e) {
            log.error("添加管理员异常", e);
        }
        return toAjax(num);
    }


    /**
     * 编辑管理员
     *
     * @param creditUserDto
     * @return
     */
    @PostMapping("/admin/editAdmin")
    public AjaxResult editAdmin(@RequestBody CreditUserDto creditUserDto) {
        try {
            if (creditUserService.editAdmin(creditUserDto) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(creditUserDto.getId());
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("添加管理员异常", e);
        }
        return AjaxResult.error();
    }

    /**
     * admin重置密码
     *
     * @param creditUserDto
     * @return
     */
    @PostMapping("/admin/resetPassWord")
    public AjaxResult resetPassWord(@RequestBody CreditUserDto creditUserDto) {
        try {
            if (creditUserService.resetPassWord(creditUserDto) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(creditUserDto.getId());
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("重置管理员密码异常", e);
        }
        return AjaxResult.error();
    }

    /**
     * 管理员获取个人信息
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/getAdminInfo")
    public AjaxResult getAdminInfo(@RequestParam("id") Long id) {
        try {
            CreditUser admin = creditUserService.getAdminInfo(id);
            return AjaxResult.success(admin);
        } catch (Exception e) {
            log.error("管理员获取个人信息异常", e);
        }
        return AjaxResult.error();
    }

    /**
     * 管理员修改个人信息
     *
     * @param creditUserDto
     * @return
     */
    @PostMapping("/admin/adminUpdateInfo")
    public AjaxResult adminUpdateInfo(@RequestBody CreditUserDto creditUserDto) {
        try {
            if (creditUserService.adminUpdateInfo(creditUserDto) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(creditUserDto.getId());
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("管理员修改个人信息异常", e);
        }
        return AjaxResult.error();
    }

    /**
     * 管理员修改自己的密码
     *
     * @param creditUserDto
     * @return
     */
    @PostMapping("/admin/updatePassword")
    public AjaxResult updatePassword(@RequestBody CreditUserDto creditUserDto) {
        try {
            if (creditUserService.resetPassWord(creditUserDto) == 1) {
                CreditUser userCache = creditUserService.getCreditUserForRedis(creditUserDto.getId());
                redisUtil.remove(String.format(RedisConstants.PREFIX_CREDIT_USER, userCache.getPhone()));
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error("管理员修改自己的密码异常", e);
        }
        return AjaxResult.error();
    }


    /**
     * 检验手机号是否唯一
     *
     * @param phone
     * @return
     */
    @GetMapping("/admin/checkPhoneUnique")
    public AjaxResult checkPhoneUnique(@RequestParam("phone") String phone) {
        Integer num = 0;
        try {
            num = creditUserService.checkPhoneUnique(phone);
        } catch (Exception e) {
            log.error("检验手机号是否唯一异常", e);
        }
        return AjaxResult.success(num);
    }

    /**
     * 修改用户身份证信息
     *
     * @author samphin
     * @date 2019-10-15 09:20:27
     */
    @PatchMapping("/admin/idCard")
    public AjaxResult updateUserIdCardInfo(@RequestBody CreditUserIdCardDto idCardDto) {
        CreditUserIdCardDtoChecktor.check(idCardDto);
        boolean bl = creditUserService.updateUserIdCardInfo(idCardDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取所有非管理员用户
     *
     * @return
     * @author samphin
     * @date 2019-10-18 15:13:29
     */
    @GetMapping("/admin/not/admin/users")
    public AjaxResult queryAllNotAdmins(@ModelAttribute CreditNotAdminUserDto queryDto) {
        startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        List<CreditNotAdminUserVo> voList = creditUserService.queryAllNotAdmins(queryDto);
        return AjaxResult.success(getNewPageData(voList));
    }
}

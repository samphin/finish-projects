package com.ryit.walletuserserver.service;

import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.dto.WalletUserDto;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.WalletUserVo;

import java.util.List;

public interface WalletUserService {

    /**
     * 电话号码查询用户信息
     *
     * @param phone
     * @return
     */
    WalletUser getWalletUserByPhone(String phone);

    /**
     * 注册新用户
     *
     * @param user
     * @return
     */
    Integer registerUser(WalletUser user);

    /**
     * 注册新用户返回用户id
     *
     * @param user
     * @return
     */
    Long registerWalletUser(WalletUser user);

    /**
     * 判断电话号码是否注册
     *
     * @param phone
     * @return
     */
    Boolean checkPhone(String phone);

    /**
     * 修改用户是否接受消息推送
     *
     * @param id
     * @return
     */
    Integer updateMessageFlag(Long id);

    /**
     * 修改用户昵称或者头像
     *
     * @param walletUser
     * @return
     */
    Boolean updateUserNameOrAvatar(WalletUser walletUser);

    /**
     * 修改用户密码
     *
     * @param walletUser
     * @return
     */
    Boolean updatePassword(WalletUser walletUser);

    /**
     * 用户信息认证
     *
     * @param sysOrder
     * @return
     */
    Boolean certifiedInformation(SysOrderDto sysOrder);

    /**
     * 条件查询用户列表
     *
     * @param dto
     * @return
     */
    List<WalletUser> queryUserByCondition(WalletUserDto dto);

    /**
     * 用户id查询用户详情
     *
     * @param id
     * @return
     */
    WalletUser getUserById(Long id);

    /**
     * 查询订单贷款人信息
     *
     * @param orderId
     * @return
     */
    WalletUser queryOrderWalletor(Long orderId);

    /**
     * 查询用户部分信息 (用于存储到redis)
     *
     * @param id
     * @return
     */
    WalletUser getUserSimple(Long id);

    /**
     * 查询用户信息及订单数量
     *
     * @param id
     * @return
     */
    WalletUserVo queryUserInfo(Long id);

    /**
     * 电话号码查询用户信息
     *
     * @param phone
     * @return
     */
    WalletUser getUserInformation(String phone);

    /**
     * 新用户
     *
     * @param walletUser
     * @return
     */
    Long insertWalletUser(WalletUser walletUser);

    /**
     * 微信注册用户openId查询用户
     *
     * @param openId
     * @return
     */
    WalletUser queryByOpenId(String openId);

    /**
     * 查询用户的身份证和真实姓名(用于提交订单用户信息认证时的信息回显)
     *
     * @param id
     * @return
     */
    WalletUser queryIdCardAndName(Long id);

    /**
     * 根据用户手机号修改身份证号
     *
     * @param walletUser
     * @return
     */
    boolean updateIdCardByPhone(WalletUser walletUser);

    /**
     * 用于已用手机号注册用户使用微信登录
     *
     * @param walletUser
     * @return
     */
    boolean updateUserOpenId(WalletUser walletUser);

    /**
     * 添加管理员
     *
     * @param walletUserDto
     * @return
     */
    boolean insertAdmin(WalletUserDto walletUserDto);

    /**
     * 修改管理员信息
     *
     * @param walletUserDto
     * @return
     */
    boolean updateAdmin(WalletUserDto walletUserDto);

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    boolean deleteAdmin(Long id);

    /**
     * id查询管理员详情
     *
     * @param id
     * @return
     */
    WalletUserVo queryAdminBy(Long id);

    /**
     * 查询管理员列表
     *
     * @param dto
     * @return
     */
    List<WalletUserVo> queryAdminList(WalletUserDto dto);

    /**
     * 管理员查询用户列表
     *
     * @param dto
     * @return
     */
    List<WalletUser> queryUserList(WalletUserDto dto);
}

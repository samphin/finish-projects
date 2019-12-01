package com.ryit.credituserserver.service;

import com.ryit.commons.entity.dto.CreditNotAdminUserDto;
import com.ryit.commons.entity.dto.CreditUserDto;
import com.ryit.commons.entity.dto.CreditUserIdCardDto;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.pojo.CreditUserCard;
import com.ryit.commons.entity.vo.CreditNotAdminUserVo;
import com.ryit.commons.entity.vo.CreditUserInviteInfoVo;
import com.ryit.commons.entity.vo.CreditUserVo;

import java.util.List;
import java.util.Map;

/**
 * @author liuxi
 */
public interface CreditUserService {

    CreditUser getCreditUserByPhone(String phone);

    Integer registerUser(CreditUser user);

    /**
     * 修改用户信息
     *
     * @param user
     * @author samphin
     * @date 2019-10-14 16:53:08
     */
    boolean updateUserInfo(CreditUser user);

    /**
     * 修改用户身份证信息
     *
     * @param idCardDto
     * @return
     */
    boolean updateUserIdCardInfo(CreditUserIdCardDto idCardDto);

    /**
     * 根据手机号查询抢单用户
     *
     * @author samphin
     * @date 2019-10-14 16:53:08
     */
    CreditUser queryUserByPhone(String phone);

    /**
     * 新增用户并返回用户主键
     *
     * @author samphin
     * @date 2019-10-14 17:52:06
     */
    Long insertCreditUserCallBack(CreditUser record);

    Integer getBackOrderNum(Long id);

    Integer updateCreditUserCoin(Long id, Double coin);

    Map<String, Object> getCreditUser(Long id);

    Double getCreditUserCoin(Long id);

    Integer modifyBaseInfo(CreditUserDto user);

    Integer modifyPassword(CreditUserDto user);

    Integer modifyPhone(CreditUserDto user);

    Integer uploadIdentityImg(CreditUserCard userCard);

    Integer commitAuthInfo(CreditUserDto user);

    CreditUserVo getAuthInfo(Long id);

    Integer getRechargeNum(Long account);

    void addBackOrderNum(Long id);

    boolean subtractBackOrderNum(Long id, Double coin);

    Boolean checkPhone(String phone);

    Integer reserPassword(CreditUserDto user);

    CreditUser getCreditUserForRedis(Long id);

    Boolean changeMessageFlag(CreditUserDto creditUserDto);

    /**
     * 根据用户ID查询用户邀请信息
     *
     * @param account
     * @return
     */
    CreditUserInviteInfoVo queryUserInviteInfo(Long account);

    /**
     * 查询所有非管理员用户
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:58:36
     */
    List<CreditUser> queryAllCreditUsers(Integer adminFlag);

    /**
     * 查询信贷员列表
     * @param creditNotAdminUserDto
     * @return
     */
    List<CreditNotAdminUserVo> queryAllNotAdmins(CreditNotAdminUserDto creditNotAdminUserDto);

    /**
     * 根据用户ID查询用户信息
     *
     * @param id
     * @return
     * @author samphin
     * @date 2019-9-17 10:58:36
     */
    List<CreditUser> queryCreditUserById(Long id);

    /**
     * 根据openId查询用户手机号、密码
     *
     * @author samphin
     * @date 2019-9-16 13:38:15
     */
    CreditUser queryCreditUserByOpenId(String openId);

    /**
     * 查询用户列表
     *
     * @param dto
     * @return
     */
    List<CreditUserVo> getCreditUserByPage(CreditUserDto dto);

    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    CreditUserVo getCreditUserInfo(Long id);

    /**
     * 审核用户
     *
     * @param creditUser
     * @return
     */
    Integer updateAuthFlag(CreditUser creditUser);

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    Integer deleteAdmin(Long id, Long loginUserId) throws Exception;

    /**
     * 添加管理员
     */
    Integer addAdmin(CreditUserDto creditUserDto);

    /**
     * 编辑管理员信息
     *
     * @param creditUserDto
     * @return
     */
    Integer editAdmin(CreditUserDto creditUserDto);

    /**
     * 重置用户密码（仅限重置管理员）
     *
     * @param creditUserDto
     * @return
     */
    Integer resetPassWord(CreditUserDto creditUserDto);

    /**
     * 管理员获取个人信息
     *
     * @param id
     * @return
     */
    CreditUser getAdminInfo(Long id);

    /**
     * 管理员修改个人信息
     *
     * @param creditUserDto
     * @return
     */
    Integer adminUpdateInfo(CreditUserDto creditUserDto);

    /**
     * 获取管理员列表
     *
     * @param creditUser
     * @return
     */
    List<CreditUserVo> getAdminList(CreditUserDto creditUser);

    /**
     * 检验手机号是否为一
     *
     * @param phone
     * @return
     */
    Integer checkPhoneUnique(String phone);

}

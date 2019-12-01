package com.ryit.credituserserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.dto.CreditNotAdminUserDto;
import com.ryit.commons.entity.dto.CreditUserDto;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.pojo.CreditUserCard;
import com.ryit.commons.entity.vo.CreditUserInviteInfoVo;
import com.ryit.commons.entity.vo.CreditUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxiu
 */
@Repository
public interface CreditUserMapper extends BaseMapper<Integer, CreditUser> {

    Integer getBackOrderNum(Long id);

    CreditUser getCreditUserByPhone(String phone);

    Integer insertCreditUser(CreditUser record);

    Integer updateCreditUserCoin(Map<String, Object> map);

    Map<String, Object> getCreditUser(Long id);

    Double getCreditUserCoinById(Long id);

    Integer updateCreditUser(Map<String, Object> map);

    /**
     * 根据手机号将用户升级成为管理员
     *
     * @param phone
     * @return
     * @author samphin
     * @date 2019-10-18 14:02:03
     */
    Integer upgradeToAdminByPhone(@Param("phone") String phone);

    String getPassword(Long id);

    int insertCreditUserCard(CreditUserCard record);

    int updateCreditUserCard(CreditUserCard record);

    CreditUserVo getAuthInfo(Long id);

    Integer getRechargeNum(Long userId);

    Long getCreditUserIdByInviteCode(String inviteCode);

    Integer checkInviteCode(String inviteCode);

    CreditUser getCreditUserById(Long id);

    Integer checkPhone(String phone);

    /**
     * 根据手机号查询抢单用户
     *
     * @param phone
     * @author samphin
     * @date 2019-10-14 17:05:03
     */
    CreditUser selectUserByPhone(@Param("phone") String phone);

    CreditUser getCreditUserByOpenId(@Param("openId") String openId);

    Integer reserPassword(Map<String, Object> map);

    CreditUser getCreditUserForRedis(Long id);

    Integer updateMessageFlag(Map map);

    /**
     * 根据用户ID，查询用户邀请信息
     *
     * @param userId
     * @return
     */
    CreditUserInviteInfoVo selectUserInviteInfo(@Param("userId") Long userId);

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
    CreditUserVo getCreditUserInfo(@Param("id") Long id);

    /**
     * 根据是否为管理员查询所有用户
     *
     * @return
     * @author samphin
     * @date 2019-9-17 10:58:36
     */
    List<CreditUser> selectAllCreditUsers(@Param("adminFlag") Integer adminFlag);

    /**
     * 根据条件筛选非管理员列表
     *
     * @param queryDto
     * @return
     * @author samphin
     * @date 2019-10-18 15:43:21
     */
    List<CreditUser> selectAllNotAdmins(CreditNotAdminUserDto queryDto);

    /**
     * 根据用户ID查询用户信息
     *
     * @param id
     * @return
     * @author samphin
     * @date 2019-9-17 10:58:36
     */
    List<CreditUser> selectListByPrimaryKey(@Param("id") Long id);

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
    Integer deleteAdmin(Long id);

    /**
     * 添加管理员
     *
     * @param dto
     * @return
     */
    Integer addAdmin(CreditUserDto dto);

    /**
     * 将用户退单次数减1，并退还用户支付金额
     *
     * @author samphin
     * @date 2019-9-3 20:25:40
     */
    Integer subtractBackOrderNum(Map map);

    /**
     * 管理员获取个人信息
     *
     * @param id
     * @return
     */
    CreditUser getAdminInfo(Long id);

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
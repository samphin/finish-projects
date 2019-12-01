package com.ryit.walletuserserver.dao;

import com.ryit.commons.entity.dto.WalletUserDto;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.WalletUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletUserMapper {

    /**
     * 添加用户信息
     *
     * @param record
     * @return
     */
    int insertWalletUser(WalletUser record);

    /**
     * 用户电话号码查询用户信息
     *
     * @param phone
     * @return
     */
    WalletUser getWalletUserByPhone(String phone);

    /**
     * 判断电话号码是否注册
     *
     * @param phone
     * @return
     */
    Integer checkPhone(String phone);

    int updateWalletUser(WalletUser walletUser);

    /**
     * 基于手机号修改贷款人身份证信息
     *
     * @author samphin
     * @author chenyongfeng
     * @date 2019-10-14 14:59:21
     */
    int updateWalletUserIdCardByPhone(WalletUser walletUser);

    /**
     * 用户id查询用户信息
     *
     * @param id
     * @return
     */
    WalletUser queryWalletUserById(Long id);

    /**
     * 用户修改密码
     *
     * @param password
     * @param phone
     * @return
     */
    Integer updatePassword(@Param("password") String password, @Param("phone") String phone);

    /**
     * 条件查询用户列表
     *
     * @param dto
     * @return
     */
    List<WalletUser> queryUserByCondition(WalletUserDto dto);

    /**
     * id查询用户信息
     *
     * @param id
     * @return
     */
    WalletUser getUserById(Long id);

    /**
     * 用户id查询用户部分信息
     *
     * @return
     */
    WalletUser getUserSimple(Long id);

    /**
     * 查询订单贷款人信息
     *
     * @param orderId
     * @return
     */
    WalletUser selectOrderWalletor(Long orderId);

    /**
     * 用户电话查询用户基本信息
     *
     * @return
     */
    WalletUser getUserInformation(String phone);

    /**
     * 查询用户信息及提交的订单数量
     *
     * @param id
     * @return
     */
    WalletUserVo queryUserInfo(Long id);

    /**
     * 用户手机号和身份证号查询
     *
     * @param idCard
     * @return
     */
    WalletUser queryByIdCard(@Param("idCard") String idCard);

    /**
     * 微信注册用户openId查询用户
     *
     * @param openId
     * @return
     */
    WalletUser queryByOpenId(String openId);

    /**
     * 查询用户的身份证和真实姓名
     *
     * @param id
     * @return
     */
    WalletUser queryIdCardAndName(@Param("id") Long id);

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    Integer deleteWalletUser(@Param("id") Long id);

    /**
     * id查询管理员详情
     *
     * @param id
     * @return
     */
    WalletUserVo queryAdminById(Long id);

    /**
     * 查询管理员列表
     *
     * @param dto
     * @return
     */
    List<WalletUserVo> queryAdminList(WalletUserDto dto);
}
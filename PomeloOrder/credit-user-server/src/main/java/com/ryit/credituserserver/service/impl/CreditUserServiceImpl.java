package com.ryit.credituserserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditNotAdminUserDto;
import com.ryit.commons.entity.dto.CreditUserDto;
import com.ryit.commons.entity.dto.CreditUserIdCardDto;
import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.pojo.CreditUserCard;
import com.ryit.commons.entity.vo.CreditNotAdminUserVo;
import com.ryit.commons.entity.vo.CreditUserInviteInfoVo;
import com.ryit.commons.entity.vo.CreditUserVo;
import com.ryit.commons.util.EndecryptUtil;
import com.ryit.commons.util.IdCardUtils;
import com.ryit.commons.util.StringUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.commons.web.exception.user.UserPasswordNotMatchException;
import com.ryit.credituserserver.dao.CreditBillMapper;
import com.ryit.credituserserver.dao.CreditRoleMapper;
import com.ryit.credituserserver.dao.CreditUserMapper;
import com.ryit.credituserserver.dao.CreditUserRoleMapper;
import com.ryit.credituserserver.feign.CreditCouponFeignClient;
import com.ryit.credituserserver.feign.MessageFeignClient;
import com.ryit.credituserserver.service.CreditUserService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreditUserServiceImpl implements CreditUserService {

    @Autowired
    private CreditUserMapper creditUserMapper;

    @Resource
    private CreditCouponFeignClient creditCouponFeignClient;

    @Resource
    private MessageFeignClient messageFeignClient;

    @Autowired
    private CreditRoleMapper creditRoleMapper;

    @Autowired
    private CreditUserRoleMapper creditUserRoleMapper;

    @Autowired
    private CreditBillMapper creditBillMapper;

    @Autowired
    private IdCardUtils idCardUtils;

    @Override
    public CreditUser getCreditUserByPhone(String phone) {
        return creditUserMapper.getCreditUserByPhone(phone);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer registerUser(CreditUser user) {
        try {
            if (null != user.getInviteCode()) {
                Long upUser = creditUserMapper.getCreditUserIdByInviteCode(user.getInviteCode());
                user.setUpUser(upUser);
            }
            String inviteCode = null;
            do {
                inviteCode = StringUtil.getRandomCode(13);
            } while (creditUserMapper.checkInviteCode(inviteCode) > 0);
            user.setInviteCode(inviteCode);
            creditUserMapper.insertCreditUser(user);
            //用户ID不为空，则领取免费优惠券
            if (null != user.getId() && user.getId() != 0) {
                JSONObject requestData = new JSONObject();
                requestData.put("userId", user.getId());
                creditCouponFeignClient.drawFreeOfChargeCoupon(requestData);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            throw new CustomException("用户注册失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserInfo(CreditUser user) {
        return creditUserMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

    /**
     * 修改用户身份证信息
     *
     * @param idCardDto
     * @author samphin
     * @date 2019-10-15 09:38:50
     */
    @Override
    public boolean updateUserIdCardInfo(CreditUserIdCardDto idCardDto) {
        //校验身份证号与真实姓名是否匹配
        if (!idCardUtils.checkIdCardAndRealName(idCardDto.getRealCode(), idCardDto.getRealName())) {
            throw new CustomException("用户身份证和真实姓名不匹配");
        }
        CreditUser user = new CreditUser();
        BeanUtils.copyProperties(idCardDto, user);
        return updateUserInfo(user);
    }

    /**
     * 根据手机号查询抢单用户
     *
     * @author samphin
     * @date 2019-10-14 16:53:08
     */
    @Override
    public CreditUser queryUserByPhone(String phone) {
        return creditUserMapper.selectUserByPhone(phone);
    }


    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertCreditUserCallBack(CreditUser record) {
        try {
            creditUserMapper.insertCreditUser(record);
            return record.getId();
        } catch (Exception e) {
            throw new CustomException("插入用户失败");
        }
    }

    @Override
    public Integer getBackOrderNum(Long id) {
        return creditUserMapper.getBackOrderNum(id);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer updateCreditUserCoin(Long id, Double coin) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", id);
            map.put("coin", coin);
            return creditUserMapper.updateCreditUserCoin(map);
        } catch (Exception e) {
            throw new CustomException("修改用户失败");
        }
    }

    @Override
    public Map<String, Object> getCreditUser(Long id) {
        return creditUserMapper.getCreditUser(id);
    }

    @Override
    public Double getCreditUserCoin(Long id) {
        return creditUserMapper.getCreditUserCoinById(id);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer modifyBaseInfo(CreditUserDto user) {
        try {
            Map<String, Object> map = new HashMap<>(3);
            map.put("id", user.getId());
            map.put("avatar", user.getAvatar());
            map.put("userName", user.getUserName());
            return creditUserMapper.updateCreditUser(map);
        } catch (Exception e) {
            throw new CustomException("修改用户信息失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer modifyPassword(CreditUserDto user) throws UserPasswordNotMatchException {
        try {
            String oldPassword = creditUserMapper.getPassword(user.getId());
            if (!oldPassword.equals(EndecryptUtil.encrytMd5(user.getPassword()))) {
                throw new UserPasswordNotMatchException();
            }
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", user.getId());
            map.put("password", EndecryptUtil.encrytMd5(user.getNewPassword()));
            return creditUserMapper.updateCreditUser(map);
        } catch (UserPasswordNotMatchException e) {
            throw new CustomException("修改密码失败");
        }
    }

    @Override
    public Boolean checkPhone(String phone) {
        return creditUserMapper.checkPhone(phone) == 0;
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer reserPassword(CreditUserDto user) {
        try {
            if (null == user || StringUtils.isEmpty(user.getPhone())) {
                return 0;
            }
            Map<String, Object> map = new HashMap<>(2);
            map.put("phone", user.getPhone());
            map.put("password", EndecryptUtil.encrytMd5(user.getPassword()));
            return creditUserMapper.reserPassword(map);
        } catch (Exception e) {
            throw new CustomException("重置密码失败");
        }
    }

    @Override
    public CreditUser getCreditUserForRedis(Long id) {
        return creditUserMapper.getCreditUserForRedis(id);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeMessageFlag(CreditUserDto creditUserDto) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("userId", creditUserDto.getId());
            map.put("messageFlag", creditUserDto.getMessageFlag());

            Integer count = this.creditUserMapper.updateMessageFlag(map);

            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("修改信息接收状态失败");
        }
    }

    @Override
    public CreditUserInviteInfoVo queryUserInviteInfo(Long account) {
        return creditUserMapper.selectUserInviteInfo(account);
    }

    @Override
    public List<CreditUser> queryAllCreditUsers(Integer adminFlag) {
        List<CreditUser> creditUsers = creditUserMapper.selectAllCreditUsers(adminFlag);
        return creditUsers;
    }

    /**
     * 查询信贷员列表
     * @param creditNotAdminUserDto
     * @return
     */
    @Override
    public List<CreditNotAdminUserVo> queryAllNotAdmins(CreditNotAdminUserDto creditNotAdminUserDto){
        List<CreditUser> creditUsers = creditUserMapper.selectAllNotAdmins(creditNotAdminUserDto);
        return CreditNotAdminUserVo.buildVoList(creditUsers);
    }

    @Override
    public List<CreditUser> queryCreditUserById(Long id) {
        return creditUserMapper.selectListByPrimaryKey(id);
    }

    @Override
    public CreditUser queryCreditUserByOpenId(String openId) {
        return creditUserMapper.getCreditUserByOpenId(openId);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer modifyPhone(CreditUserDto user) {
        if (creditUserMapper.checkPhone(user.getPhone()) > 0) {
            throw new CustomException("该手机号已注册！");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", user.getId());
        map.put("phone", user.getPhone());
        return creditUserMapper.updateCreditUser(map);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer uploadIdentityImg(CreditUserCard userCard) {
        try {
            return creditUserMapper.insertCreditUserCard(userCard);
        } catch (Exception e) {
            throw new CustomException("保存身份证照片失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer commitAuthInfo(CreditUserDto user) {
        try {
            Map<String, Object> map = new HashMap<>(14);
            map.put("id", user.getId());
            map.put("city", user.getCity());
            map.put("companyType", user.getCompanyType());
            map.put("companyName", user.getCompanyName());
            map.put("companyAddr", user.getCompanyAddr());
            map.put("realName", user.getRealName());
            map.put("realCode", user.getRealCode());
            map.put("issuingUnit", user.getIssuingUnit());
            map.put("activeLife", user.getActiveLife());
            map.put("groupPhoto", user.getGroupPhoto());
            map.put("workCard", user.getWorkCard());
            map.put("businessCard", user.getBusinessCard());
            map.put("contractSignature", user.getContractSignature());
            if (null != user.getRealCode()) {
                map.put("sex", IdCardUtils.getSexByIdCard(user.getRealCode()));
            }
            map.put("authFlag", 1);
            return creditUserMapper.updateCreditUser(map);
        } catch (Exception e) {
            throw new CustomException("提交用户信息失败");
        }
    }

    @Override
    public CreditUserVo getAuthInfo(Long id) {
        return creditUserMapper.getAuthInfo(id);
    }

    @Override
    public Integer getRechargeNum(Long userId) {
        return creditUserMapper.getRechargeNum(userId);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public void addBackOrderNum(Long id) {
        try {
            Integer num = creditBillMapper.countGrab(id);
            if (num % 6 == 0) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("id", id);
                map.put("backOrderNum", 1);
                creditUserMapper.updateCreditUserCoin(map);
            }
        } catch (Exception e) {
            throw new CustomException("添加退单数量失败");
        }
    }

    /**
     * 减掉退单次数
     *
     * @param id   用户ID
     * @param coin 退还金额
     * @author samphin
     * @date 2019-9-3 20:22:08
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean subtractBackOrderNum(Long id, Double coin) {
        try {
            //修改退还金额
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", id);
            map.put("coin", coin);
            int count = creditUserMapper.subtractBackOrderNum(map);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("减少退单次数失败");
        }
    }

    /**
     * 查询用户列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<CreditUserVo> getCreditUserByPage(CreditUserDto dto) {
        try {
            return creditUserMapper.getCreditUserByPage(dto);
        } catch (Exception e) {
            throw new CustomException("请求用户列表错误", e);
        }
    }

    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    @Override
    public CreditUserVo getCreditUserInfo(Long id) {
        try {
            return creditUserMapper.getCreditUserInfo(id);
        } catch (Exception e) {
            throw new CustomException("请求用户详情错误", e);
        }
    }

    /**
     * 审核用户
     *
     * @param creditUser
     * @return
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer updateAuthFlag(CreditUser creditUser) {
        try {
            String title = "用户认证提醒";
            String content = "";
            switch (creditUser.getAuthFlag()) {
                //已认证，认证通过
                case 2:
                    content = "您已在在" + DateFormatUtils.format(new Date(), "yyyy年MM月dd日") + "通过柚子抢单APP用户认证";
                    break;
                //认证失败，认证未通过
                case 3:
                    content = "您已在在" + DateFormatUtils.format(new Date(), "yyyy年MM月dd日") + "在柚子抢单APP进行用户认证时失败，请核实认证材料";
                    break;
            }
            List<CreditUser> users = this.creditUserMapper.selectListByPrimaryKey(creditUser.getId());
            PushMessageDto messageDto = new PushMessageDto();
            messageDto.setTitle(title);
            messageDto.setContent(content);
            messageDto.setUsers(users);
            //柚子抢单消息
            messageDto.setAppType("credit");
            //极光推送消息给充值用户
            messageFeignClient.push(messageDto);
            return creditUserMapper.updateAuthFlag(creditUser);
        } catch (Exception e) {
            throw new CustomException("审核用户失败");
        }
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteAdmin(Long id, Long loginUserId) throws Exception {
        if (id == 1L) {
            throw new Exception("不允许删除超级管理员用户");
        }
        if (loginUserId.longValue() == id.longValue()) {
            throw new Exception("不允许删除自己的账户");
        }
        return creditUserMapper.deleteAdmin(id);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer addAdmin(CreditUserDto creditUserDto) {
        try {
            //新增管理员时通过手机号判断用户是否存在
            Integer isExist = creditUserMapper.checkPhone(creditUserDto.getPhone());
            //如果手机号已存在，则直接将用户升级为管理员
            if (isExist > 0) {
                int count = creditUserMapper.upgradeToAdminByPhone(creditUserDto.getPhone());
                return count;
            } else {
                creditUserDto.setAdminFlag(1);
                creditUserDto.setPassword(EndecryptUtil.encrytMd5(creditUserDto.getPassword()));
                int count = creditUserMapper.addAdmin(creditUserDto);
                if (count > 0) {
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("userId", creditUserDto.getId());
                    map.put("roleId", creditUserDto.getRoleId());
                    creditUserRoleMapper.saveUserRole(map);
                }
                return count;
            }
        } catch (Exception e) {
            throw new CustomException("添加管理员失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer editAdmin(CreditUserDto creditUserDto) {
        try {
            Map<String, Object> map = new HashMap<>(7);
            map.put("id", creditUserDto.getId());
            map.put("userName", creditUserDto.getUserName());
            map.put("email", creditUserDto.getEmail());
            map.put("sex", creditUserDto.getSex());
            map.put("city", creditUserDto.getCity());
            int num = creditUserMapper.updateCreditUser(map);
            if (num > 0) {
                creditUserRoleMapper.deleteUserRole(creditUserDto.getId());
                map.put("userId", creditUserDto.getId());
                map.put("roleId", creditUserDto.getRoleId());
                creditUserRoleMapper.saveUserRole(map);
            }
            return num;
        } catch (Exception e) {
            throw new CustomException("修改管理员失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer resetPassWord(CreditUserDto creditUserDto) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", creditUserDto.getId());
            map.put("password", EndecryptUtil.encrytMd5(creditUserDto.getNewPassword()));
            return creditUserMapper.updateCreditUser(map);
        } catch (Exception e) {
            throw new CustomException("重设密码失败");
        }
    }

    @Override
    public CreditUser getAdminInfo(Long id) {
        return creditUserMapper.getAdminInfo(id);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer adminUpdateInfo(CreditUserDto creditUserDto) {
        try {
            Map<String, Object> map = new HashMap<>(6);
            map.put("id", creditUserDto.getId());
            map.put("userName", creditUserDto.getUserName());
            map.put("email", creditUserDto.getEmail());
            map.put("sex", creditUserDto.getSex());
            map.put("phone", creditUserDto.getPhone());
            map.put("avatar", creditUserDto.getAvatar());
            return creditUserMapper.updateCreditUser(map);
        } catch (Exception e) {
            throw new CustomException("修改管理员信息失败");
        }
    }

    @Override
    public List<CreditUserVo> getAdminList(CreditUserDto creditUser) {
        try {
            return creditUserMapper.getAdminList(creditUser);
        } catch (Exception e) {
            throw new CustomException("查询管理员列表错误", e);
        }
    }

    @Override
    public Integer checkPhoneUnique(String phone) {
        return creditUserMapper.checkPhone(phone);
    }
}

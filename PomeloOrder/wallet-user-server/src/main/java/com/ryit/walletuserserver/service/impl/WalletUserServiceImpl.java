package com.ryit.walletuserserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.dto.WalletUserDto;
import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.entity.vo.WalletUserVo;
import com.ryit.commons.util.EndecryptUtil;
import com.ryit.commons.util.IdCardUtils;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletuserserver.dao.WalletRoleMapper;
import com.ryit.walletuserserver.dao.WalletUserMapper;
import com.ryit.walletuserserver.feign.SysOrderFeignClient;
import com.ryit.walletuserserver.service.WalletUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class WalletUserServiceImpl implements WalletUserService {

    private Logger log = LoggerFactory.getLogger(WalletUserServiceImpl.class);

    @Autowired
    private WalletUserMapper walletUserMapper;

    @Resource
    private SysOrderFeignClient sysOrderFeignClient;

    @Autowired
    private IdCardUtils idCardUtils;

    @Autowired
    private WalletRoleMapper walletRoleMapper;

    @Override
    public WalletUser getWalletUserByPhone(String phone) {
        return walletUserMapper.getWalletUserByPhone(phone);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer registerUser(WalletUser user) {
        return walletUserMapper.insertWalletUser(user);
    }

    @Override
    public Long registerWalletUser(WalletUser user) {
        user.setReleaseTime(new Date());
        walletUserMapper.insertWalletUser(user);
        return user.getId();
    }

    @Override
    public Boolean checkPhone(String phone) {
        return walletUserMapper.checkPhone(phone) > 0 ? true : false;
    }


    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer updateMessageFlag(Long id) {
        try {
            WalletUser walletUser = walletUserMapper.queryWalletUserById(id);
            if (walletUser != null) {
                //接受消息推送
                if (walletUser.getMessageFlag() == null || walletUser.getMessageFlag().equals(0)) {
                    //设置为不接受推送
                    walletUser.setMessageFlag(1);
                    //不接受消息推送
                } else if (walletUser.getMessageFlag().equals(1)) {
                    //设置为接受消息推送
                    walletUser.setMessageFlag(0);
                } else {
                    return null;
                }
                walletUserMapper.updateWalletUser(walletUser);
                //返回设置后的消息推送接受状态
                return walletUser.getMessageFlag();
            }
            return null;
        } catch (Exception e) {
            throw new CustomException("用户修改消息推送状态失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserNameOrAvatar(WalletUser walletUser) {
        try {
            WalletUser user = walletUserMapper.queryWalletUserById(walletUser.getId());
            if (user == null) {
                throw new CustomException("用户信息异常");
            }
            if (StringUtils.isNotBlank(walletUser.getAvatar())) {
                user.setAvatar(walletUser.getAvatar());
            }
            if (StringUtils.isNotBlank(walletUser.getUserName())) {
                user.setUserName(walletUser.getUserName());
            }
            walletUserMapper.updateWalletUser(walletUser);
            return true;
        } catch (Exception e) {
            throw new CustomException("用户修改头像和昵称失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassword(WalletUser walletUser) {
        try {
            return walletUserMapper.updatePassword(walletUser.getPassword(), walletUser.getPhone()) > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("用户重置密码失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean certifiedInformation(SysOrderDto sysOrder) {
        WalletUser walletUser = sysOrder.getUserInfo();
        if (!idCardUtils.checkIdCardAndRealName(walletUser.getCreditorIdcard(), walletUser.getRealname())) {
            throw new CustomException("用户身份证和真实姓名不匹配");
        }
        try {
            walletUserMapper.updateWalletUser(walletUser);
            //远程调用订单接口 提交订单
            sysOrder.setSourceUrl(walletUser.getSourceUrl());
            AjaxResult ajaxResult = sysOrderFeignClient.insertSysOrder(sysOrder);
            if (ajaxResult.getCode() == 1) {
                return true;
            }
        } catch (Exception e) {
            throw new CustomException("用户提交订单失败");
        }
        return false;
    }

    @Override
    public List<WalletUser> queryUserByCondition(WalletUserDto dto) {
        return walletUserMapper.queryUserByCondition(dto);
    }

    @Override
    public WalletUser getUserById(Long id) {
        return walletUserMapper.getUserById(id);
    }

    @Override
    public WalletUser getUserSimple(Long id) {
        return walletUserMapper.getUserSimple(id);
    }

    @Override
    public WalletUser queryOrderWalletor(Long orderId) {
        return walletUserMapper.selectOrderWalletor(orderId);
    }

    @Override
    public WalletUserVo queryUserInfo(Long id) {
        return walletUserMapper.queryUserInfo(id);
    }

    @Override
    public WalletUser getUserInformation(String phone) {
        try {
            WalletUser userInformation = walletUserMapper.getUserInformation(phone);
            return userInformation;
        } catch (Exception e) {
            throw new CustomException("查询失败");
        }
    }

    /**
     * 保存钱包用户信息
     *
     * @param walletUser
     * @return
     * @author samphin
     * @date 2019-10-8 17:13:07
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletUser(WalletUser walletUser) {

        String idCard = walletUser.getCreditorIdcard();
        if (StringUtils.isNotBlank(idCard)) {
            //设置性别
            walletUser.setSex(IdCardUtils.getSexByIdCard(idCard));
            //根据身份证获取年龄
            walletUser.setAge(IdCardUtils.getAgeByIdCard(idCard));
            //通过身份证查询是否绑定用户
            WalletUser user = walletUserMapper.queryByIdCard(idCard);
            if (null != user) {
                throw new CustomException("用户身份证已被绑定");
            }
            //校验身份证号与真实姓名是否匹配
            if (!idCardUtils.checkIdCardAndRealName(idCard, walletUser.getRealname())) {
                throw new CustomException("用户身份证和真实姓名不匹配");
            }
        }

        if (StringUtils.isNotBlank(walletUser.getPhone()) && null == walletUser.getId()) {
            WalletUser u = walletUserMapper.getWalletUserByPhone(walletUser.getPhone());
            if (null != u) {
                throw new CustomException("电话号码已被注册");
            }
        }

        try {
            if (null != walletUser.getId()) {
                walletUserMapper.updateWalletUser(walletUser);
            } else {
                walletUserMapper.insertWalletUser(walletUser);
            }
            return walletUser.getId();
        } catch (Exception e) {
            log.error("钱包用户保存失败", e);
            throw new CustomException("钱包用户保存失败");
        }
    }

    @Override
    public WalletUser queryByOpenId(String openId) {
        return walletUserMapper.queryByOpenId(openId);
    }

    @Override
    public WalletUser queryIdCardAndName(Long id) {
        return walletUserMapper.queryIdCardAndName(id);
    }

    @Override
    public boolean updateIdCardByPhone(WalletUser walletUser) {
        int count = walletUserMapper.checkPhone(walletUser.getPhone());
        if (count < 1) {
            throw new CustomException("当前手机号尚未注册,无法进行修改操作");
        }
        //校验身份证号与真实姓名是否匹配
        if (!idCardUtils.checkIdCardAndRealName(walletUser.getCreditorIdcard(), walletUser.getRealname())) {
            throw new CustomException("用户身份证和真实姓名不匹配");
        }
        return walletUserMapper.updateWalletUserIdCardByPhone(walletUser) > 0 ? true : false;
    }

    @Override
    public boolean updateUserOpenId(WalletUser walletUser) {
        return walletUserMapper.updateWalletUser(walletUser) > 0 ? true : false;
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertAdmin(WalletUserDto walletUserDto) {
        WalletUser walletUser = walletUserMapper.getUserInformation(walletUserDto.getPhone());
        if (null != walletUser && walletUser.getAdminFlag() == 1) {
            throw new CustomException("该电话号码绑定的用户已拥有管理员权限,请勿重复操作");
        }
        try {
            Long roleId = walletUserDto.getRoleId();
            Integer num = null;
            if (null != walletUser) {
                walletUser.setAdminFlag(1);
                num = walletUserMapper.updateWalletUser(walletUser);
                walletRoleMapper.deleteAllUserRole(walletUser.getId());
            } else {
                BeanUtils.copyProperties(walletUserDto, walletUser);
                walletUser.setReleaseTime(new Date());
                walletUser.setAdminFlag(1);
                walletUser.setPassword(EndecryptUtil.encrytMd5(walletUser.getPassword()));
                num = walletUserMapper.insertWalletUser(walletUser);
            }
            if (null != roleId) {
                walletRoleMapper.insertUserRole(walletUser.getId(), roleId);
            }
            return num > 0 ? true : false;
        } catch (Exception e) {
            log.error("保存钱包管理员失败", e);
            throw new CustomException("保存钱包管理员失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAdmin(WalletUserDto walletUserDto) {
        try {
            Long roleId = walletUserDto.getRoleId();
            WalletUser walletUser = new WalletUser();
            BeanUtils.copyProperties(walletUserDto, walletUser);
            walletUser.setReleaseTime(new Date());
            walletUser.setAdminFlag(1);
            walletUser.setPassword(EndecryptUtil.encrytMd5(walletUser.getPassword()));
            Integer num = walletUserMapper.updateWalletUser(walletUser);
            if (null != roleId) {
                //删除所有的关联角色
                walletRoleMapper.deleteAllUserRole(walletUser.getId());
                //添加新的关联角色
                walletRoleMapper.insertUserRole(walletUser.getId(), roleId);
            }
            return num > 0 ? true : false;
        } catch (Exception e) {
            log.error("修改钱包管理员失败", e);
            throw new CustomException("修改钱包管理员失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAdmin(Long id) {
        try {
            //删除用户信息
            Integer num = walletUserMapper.deleteWalletUser(id);
            //删除用户关联角色信息
            walletRoleMapper.deleteAllUserRole(id);
            return num > 0 ? true : false;
        } catch (Exception e) {
            log.error("修改钱包管理员失败", e);
            throw new CustomException("修改钱包管理员失败");
        }
    }

    @Override
    public WalletUserVo queryAdminBy(Long id) {
        try {
            return walletUserMapper.queryAdminById(id);
        } catch (Exception e) {
            log.error("查询管理员详情失败", e);
            throw new CustomException("查询管理员详情失败");
        }
    }

    @Override
    public List<WalletUserVo> queryAdminList(WalletUserDto dto) {
        try {
            return walletUserMapper.queryAdminList(dto);
        } catch (Exception e) {
            log.error("查询管理员列表失败", e);
            throw new CustomException("查询管理员列表失败");
        }
    }

    @Override
    public List<WalletUser> queryUserList(WalletUserDto dto) {
        try {
            dto.setAdminFlag(0);
            return walletUserMapper.queryUserByCondition(dto);
        } catch (Exception e) {
            log.error("查询管理员列表失败", e);
            throw new CustomException("查询管理员列表失败");
        }
    }

}

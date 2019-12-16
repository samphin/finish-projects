package com.ryit.users.service;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.RolesPermissionsVo;
import com.ryit.commons.entity.vo.SysBuyerListVo;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import com.ryit.commons.entity.vo.SysUserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISysUserService extends IBaseService<Integer, SysUserDto, SysUserVo> {

    /**
     * 用户注册
     *
     * @param dto
     * @return
     */
    boolean register(SysUserDto dto);

    /**
     * 保存用户绑定的角色信息
     *
     * @param dto
     * @return
     */
    boolean bindingRoles(SysUserRolePkDto dto);

    /**
     * 查询用户已拥有的角色信息
     *
     * @param userId
     * @return
     */
    List<SysSimpleRoleVo> queryUserHaveRoles(Integer userId);

    /**
     * 查询用户未拥有的角色信息
     *
     * @param dto
     * @return
     */
    List<SysSimpleRoleVo> queryUserHaveNoRoles(SysUserRolePkQueryDto dto);

    /**
     * 修改用户密码
     *
     * @param passwordDto
     * @return
     */
    boolean updatePassword(SysUserPasswordDto passwordDto, HttpServletRequest request);

    /**
     * 修改用户状态
     *
     * @param stateDto
     * @return
     */
    boolean updateState(SysUserStateDto stateDto);

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone
     * @return SysUser
     * @author samphin
     * @since 2019-10-16 17:43:37
     */
    SysUser queryLoginInfoByPhone(String telephone);

    /**
     * APP查询我的信息
     *
     * @param id
     * @return
     */
    SysUserVo queryMyInfo(Integer id);

    /**
     * 查询用户订单数量
     *
     * @param userId
     * @return
     */
    Integer queryUserOrderNums(Integer userId);

    /**
     * 校验手机号是否存在
     *
     * @param telephone
     * @return Integer
     * @author samphin
     * @since 2019-10-16 17:46:28
     */
    boolean existTelephone(String telephone);

    /**
     * 根据用户ID查询用户角色信息
     *
     * @return
     */
    RolesPermissionsVo queryRolesPermissionsById(Integer id);

    /**
     * APP修改手机号码
     *
     * @param dto
     * @param request
     * @return
     */
    boolean updateMobilePhone(SysUserAppMobilePhoneDto dto, HttpServletRequest request);

    /**
     * APP修改用户密码
     *
     * @param dto
     * @param request
     * @return
     */
    boolean updatePasswordForApp(SysUserAppPasswordDto dto, HttpServletRequest request);

    /**
     * 查询所有买家列表
     *
     * @param queryDto
     * @return
     */
    PageBean<SysBuyerListVo> queryBuyers(BaseQueryDto<SysBuyerQueryDto> queryDto);

    /**
     * 查询所有咨询用户列表
     *
     * @param queryDto
     * @return
     */
    List<SysBuyerListVo> queryAdvisoryUserList(SysBuyerQueryDto queryDto);
}

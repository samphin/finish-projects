package com.ryit.users.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.pojo.BusiMembership;
import com.ryit.commons.entity.pojo.SysRole;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.pojo.SysUserRolePk;
import com.ryit.commons.entity.vo.RolesPermissionsVo;
import com.ryit.commons.entity.vo.SysBuyerListVo;
import com.ryit.commons.entity.vo.SysSimpleRoleVo;
import com.ryit.commons.entity.vo.SysUserVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.Md5Util;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.users.feign.IBusiOrderFeignClient;
import com.ryit.users.mapper.*;
import com.ryit.users.service.ISysUserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<Integer, SysUserDto, SysUserVo> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRolePkMapper sysUserRolePkMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private BusiMembershipMapper busiMembershipMapper;

    @Resource
    private IBusiOrderFeignClient busiOrderFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 注册
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(SysUserDto dto) {
        try {
            SysUser sysUser = new SysUser().buildPo(dto);
            //给密码加密
            String pwd = Md5Util.encrypt(sysUser.getPassword());
            sysUser.setPassword(pwd);
            //新增用户
            sysUserMapper.insertSelective(sysUser);

            Integer userId = sysUser.getId();
            //用户注册即添加会员信息
            BusiMembership po = new BusiMembership();
            po.setId(SnowflakeIdWorker.generateId());
            po.setUserId(userId);
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());
            busiMembershipMapper.insertSelective(po);
            //默认设置为买家角色
            SysUserRolePkDto userRolePkDto = new SysUserRolePkDto();
            userRolePkDto.setUserId(userId);
            //设置买家角色
            userRolePkDto.setRoleIds("5");
            bindingRoles(userRolePkDto);
            return true;
        } catch (Exception e) {
            throw new CustomException("添加用户失败", e);
        }
    }

    /**
     * 添加用户
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(SysUserDto dto) {
        try {
            SysUser sysUser = new SysUser().buildPo(dto);
            //给密码加密
            String pwd = Md5Util.encrypt(sysUser.getPassword());
            sysUser.setPassword(pwd);
            sysUserMapper.insertSelective(sysUser);
            return true;
        } catch (Exception e) {
            throw new CustomException("添加用户失败", e);
        }
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(SysUserDto dto) {
        SysUser sysUser = new SysUser().buildSysUser(dto);
        boolean bl = sysUserMapper.updateByPrimaryKeySelective(sysUser) > 0;
        return bl;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(SysUserPasswordDto passwordDto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        SysUser sysUser = new SysUser().buildSysUser(passwordDto);
        sysUser.setLastUpdateDate(new Date());
        sysUser.setLastUpdateUserId(userId);
        boolean bl = sysUserMapper.updateByPrimaryKeySelective(sysUser) > 0;
        return bl;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateState(SysUserStateDto stateDto) {
        SysUser sysUser = new SysUser().buildSysUser(stateDto);
        int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return count > 0;
    }

    @Override
    public SysUser queryLoginInfoByPhone(String telephone) {
        return sysUserMapper.selectLoginInfoByPhone(telephone);
    }

    /**
     * APP查询我的信息
     *
     * @param id
     * @return
     */
    @Override
    public SysUserVo queryMyInfo(Integer id) {
        SysUser po = sysUserMapper.selectByPrimaryKey(id);
        ResponseData<Integer> responseData = busiOrderFeignClient.queryUserOrderNums(id);
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        SysUserVo vo = new SysUserVo().buildVo(po);
        vo.setOrderNum(responseData.getData());
        return vo;
    }

    @Override
    public Integer queryUserOrderNums(Integer userId) {
        ResponseData<Integer> responseData = busiOrderFeignClient.queryUserOrderNums(userId);
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        return responseData.getData();
    }

    @Override
    public boolean existTelephone(String telephone) {
        Integer count = sysUserMapper.selectUserByPhone(telephone);
        return count > 0;
    }

    /**
     * 查询用户角色、权限信息
     *
     * @param id
     * @return
     */
    @Override
    public RolesPermissionsVo queryRolesPermissionsById(Integer id) {
        RolesPermissionsVo vo = new RolesPermissionsVo();
        List<SysRole> roles = this.sysRoleMapper.selectRolesByUserId(id);
        //角色编码
        List<String> roleCodes = roles.stream().map(SysRole::getCode).collect(Collectors.toList());
        //角色ID
        List<Integer> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
        List<String> permissionCodes = this.sysPermissionMapper.selectPermissionsByRoleIds(roleIds);
        vo.setRoles(roleCodes);
        vo.setPermissions(permissionCodes);
        return vo;
    }

    @Override
    public boolean updateMobilePhone(SysUserAppMobilePhoneDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setMobilePhone(dto.getNewMobilePhone());
        return sysUserMapper.updateByPrimaryKeySelective(sysUser) > 0;
    }

    @Override
    public boolean updatePasswordForApp(SysUserAppPasswordDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        SysUserPasswordDto passwordDto = new SysUserPasswordDto();
        passwordDto.setId(userId);
        passwordDto.setOldPassword(dto.getOldPassword());
        passwordDto.setNewPassword(dto.getNewPassword());

        //获取手机号
        String mobilePhone = dto.getMobilePhone();
        String userInfo = String.format(RedisConstants.PREFIX_USER_INFO, mobilePhone);
        //判断验证码是否已过期
        if (redisUtil.hasKey(userInfo)) {
            redisUtil.remove(String.format(RedisConstants.PREFIX_USER_INFO, mobilePhone));
            redisUtil.remove(String.format(RedisConstants.PREFIX_API_REFRESH_TOKEN, mobilePhone));
        }

        SysUser sysUser = new SysUser().buildSysUser(passwordDto);
        sysUser.setLastUpdateDate(new Date());
        sysUser.setLastUpdateUserId(userId);
        return sysUserMapper.updateByPrimaryKeySelective(sysUser) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer id) {
        return sysUserMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public PageBean<SysUserVo> queryPageList(BaseQueryDto<SysUserDto> queryDto) {

        //开启分页
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());

        SysUserDto dto = queryDto.getParam();

        //组装查询条件
        SysUser sysUser = new SysUser().buildPo(dto);

        //返回查询结果
        List<SysUser> list = this.sysUserMapper.selectList(sysUser);

        //将poList转换成voList
        List<SysUserVo> voList = new SysUserVo().buildVoList(list);

        return getPageData(voList, page);
    }

    @Override
    public SysUserVo queryById(Integer id) {
        SysUser sysUser = this.sysUserMapper.selectByPrimaryKey(id);
        SysUserVo vo = new SysUserVo().buildVo(sysUser);
        return vo;
    }

    @Override
    public List<SysUserVo> queryListByCondition(SysUserDto dto) {
        //组装查询条件
        SysUser sysUser = new SysUser(dto.getRealName(), dto.getSex(), dto.getMobilePhone());

        //返回查询结果
        List<SysUser> list = this.sysUserMapper.selectList(sysUser);

        //将poList转换成voList
        List<SysUserVo> voList = new SysUserVo().buildVoList(list);

        return voList;
    }


    /*************************用户设置角色*********************/

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean bindingRoles(SysUserRolePkDto dto) {
        List<SysUserRolePk> sysUserRolePkList = new SysUserRolePk().buildSysGroupRole(dto);
        //根据用户ID，先清除之前绑定的关联信息
        SysUserRolePk sysUserRolePk = new SysUserRolePk();
        sysUserRolePk.setUserId(dto.getUserId());
        sysUserRolePkMapper.delete(sysUserRolePk);

        //保存最新的用户与角色关联信息
        return sysUserRolePkMapper.saveBatch(sysUserRolePkList) > 0;
    }

    @Override
    public List<SysSimpleRoleVo> queryUserHaveRoles(Integer userId) {
        List<SysRole> roleList = this.sysRoleMapper.selectUserHaveRoles(userId);

        List<SysSimpleRoleVo> sysGroupRoleVoList = SysSimpleRoleVo.buildSysGroupRoleVoList(roleList);

        return sysGroupRoleVoList;
    }

    @Override
    public List<SysSimpleRoleVo> queryUserHaveNoRoles(SysUserRolePkQueryDto dto) {
        List<SysRole> roleList = this.sysRoleMapper.selectUserHaveNoRoles(dto.getUserId(), dto.getName());

        List<SysSimpleRoleVo> sysGroupRoleVoList = SysSimpleRoleVo.buildSysGroupRoleVoList(roleList);

        return sysGroupRoleVoList;
    }

    /**
     * 查询所有买家列表
     *
     * @param queryDto
     * @return
     */
    @Override
    public PageBean<SysBuyerListVo> queryBuyers(BaseQueryDto<SysBuyerQueryDto> queryDto) {

        //开启分页
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());

        //获取查询参数
        SysBuyerQueryDto dto = queryDto.getParam();

        //返回查询结果
        List<SysBuyerListVo> voList = sysUserMapper.selectBuyers(dto);

        return getPageData(voList, page);
    }
}

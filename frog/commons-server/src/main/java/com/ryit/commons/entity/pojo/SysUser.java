package com.ryit.commons.entity.pojo;

import com.ryit.commons.base.po.BasePo;
import com.ryit.commons.entity.dto.SysUserDto;
import com.ryit.commons.entity.dto.SysUserPasswordDto;
import com.ryit.commons.entity.dto.SysUserQueryDto;
import com.ryit.commons.entity.dto.SysUserStateDto;
import com.ryit.commons.util.Md5Util;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 *
 * @author samphin
 * @since 2019-10-23 15:36:13
 */
@Data
@NoArgsConstructor
public class SysUser extends BasePo<Integer, SysUserDto, SysUser> implements Serializable {

    private static final long serialVersionUID = 6840775991643139911L;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 访问类型 1-随时 2-上班时间 3-下班时间
     */
    private Integer accessDateType;

    /**
     * 性别：0-男 1-女 2-其他
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 删除标识 0:有效 1:删除
     */
    private Integer delFlag;

    /**
     * 用户状态 0-正常 1-冻结 2-注销
     */
    private Integer status;

    /**
     * 登录状态 0-离线 1-在线
     */
    private Boolean loginStatus;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 版本号，乐观锁，防止重复提交数据。
     */
    private Integer version;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 居住省份
     */
    private String province;

    /**
     * 居住城市
     */
    private String city;

    /**
     * 居住详细地址
     */
    private String address;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改人ID
     */
    private Integer lastUpdateUserId;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;


    public SysUser(String realName, Integer sex, String mobilePhone) {
        this.realName = realName;
        this.sex = sex;
        this.mobilePhone = mobilePhone;
    }

    //保存：转换Dto-->Po
    public SysUser buildSysUser(SysUserDto saveDto) {
        BeanUtils.copyProperties(saveDto, this);
        if (StringUtils.isNotBlank(saveDto.getPassword())) {
            String pwd = Md5Util.encrypt(saveDto.getPassword());
            this.setPassword(pwd);
        }
        this.setCreateDate(new Date());
        this.setLastUpdateDate(new Date());
        return this;
    }

    //修改密码：Dto-->Po
    public SysUser buildSysUser(SysUserPasswordDto passwordDto) {
        this.setId(passwordDto.getId());
        String pwd = Md5Util.encrypt(passwordDto.getNewPassword());
        this.setPassword(pwd);
        this.setLastUpdateDate(new Date());
        return this;
    }

    //修改状态：Dto-->Po
    public SysUser buildSysUser(SysUserStateDto stateDto) {
        this.setId(stateDto.getId());
        this.setStatus(stateDto.getStatus());
        this.setLastUpdateDate(new Date());
        return this;
    }

    //查询：转换Dto-->Po
    public SysUser buildSysUser(SysUserQueryDto dto) {
        BeanUtils.copyProperties(dto, this);
        return this;
    }
}
package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUserInfoVo implements Serializable {

    private static final long serialVersionUID = 1104465601473043278L;

    private Integer id;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 真实名称
     */
    private String realName;

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
     * po->vo
     *
     * @param po
     * @return
     */
    public LoginUserInfoVo buildVo(SysUser po) {
        BeanUtils.copyProperties(po, this);
        this.setId(po.getId());
        return this;
    }
}

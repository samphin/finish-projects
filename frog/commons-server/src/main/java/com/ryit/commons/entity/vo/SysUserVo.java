package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserVo extends BaseVo<Integer, SysUser, SysUserVo> implements Serializable {

    private static final long serialVersionUID = 9023392097989162572L;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

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
     * 订单数量
     */
    private Integer orderNum;

}

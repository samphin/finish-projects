package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel
@Data
public class SysUserDto extends BaseDto<Integer> implements Serializable {

    private static final long serialVersionUID = 5497166188889721387L;

    @ApiModelProperty(value = "昵称")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 性别：0-男 1-女 2-其他
     */
    @ApiModelProperty(value = "性别", allowableValues = "0,1,2")
    private Integer sex;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobilePhone;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码", required = true)
    private String idCard;

    /**
     * 访问类型 1-随时 2-上班时间 3-下班时间
     */
    private Integer accessDateType;

    /**
     * 删除标识 0:有效 1:删除
     */
    @ApiModelProperty(value = "昵称", allowableValues = "0,1")
    private Integer delFlag;

    /**
     * 用户状态 0-正常 1-冻结 2-注销
     */
    @ApiModelProperty(value = "用户状态", example = "1", allowableValues = "0,1,2")
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

}

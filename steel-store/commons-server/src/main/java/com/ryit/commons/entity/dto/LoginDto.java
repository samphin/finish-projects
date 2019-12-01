package com.ryit.commons.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * 登录Dto
 *
 * @author samphin
 * @since 2019-10-23 16:43:23
 */
@Data
public class LoginDto implements Serializable {

    private static final long serialVersionUID = -7602382204769157924L;

    @NotBlank(message = "手机号不能为空", groups = {Login.class, Register.class})
    private String telephone;

    @NotBlank(message = "密码不能为空", groups = {Login.class, Register.class})
    private String password;

    @Null(groups = {Login.class})
    @NotBlank(message = "验证码不能为空", groups = {Register.class})
    private String validateCode;

    /**
     * 定义新增校验组
     */
    public interface Login {


    }

    /**
     * 定义修改校验组
     */
    public interface Register {


    }
}

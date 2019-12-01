package com.ryit.commons.web.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 * @author ryit
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("member.password.not.match", null);
    }
}

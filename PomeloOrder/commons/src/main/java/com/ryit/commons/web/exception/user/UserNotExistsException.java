package com.ryit.commons.web.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author ryit
 */
public class UserNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("member.not.exists", null);
    }
}

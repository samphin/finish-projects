package com.ryit.commons.web.exception.user;

/**
 * 用户账号已被删除
 * 
 * @author ryit
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("member.password.delete", null);
    }
}

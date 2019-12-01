package com.ryit.commons.web.exception.user;

/**
 * 用户锁定异常类
 * 
 * @author ryit
 */
public class UserBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException(String reason)
    {
        super("member.blocked", new Object[] { reason });
    }
}

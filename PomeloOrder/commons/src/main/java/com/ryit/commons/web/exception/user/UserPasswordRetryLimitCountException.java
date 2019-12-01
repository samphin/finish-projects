package com.ryit.commons.web.exception.user;

/**
 * 用户错误记数异常类
 * 
 * @author ryit
 */
public class UserPasswordRetryLimitCountException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super("member.password.retry.limit.count", new Object[] { retryLimitCount });
    }
}

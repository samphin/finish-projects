package com.ryit.commons.web.exception.user;

/**
 * 用户错误最大次数异常类
 * 
 * @author ryit
 */
public class UserPasswordRetryLimitExceedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount)
    {
        super("member.password.retry.limit.exceed", new Object[] { retryLimitCount });
    }
}

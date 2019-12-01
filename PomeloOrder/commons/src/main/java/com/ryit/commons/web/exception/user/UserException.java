package com.ryit.commons.web.exception.user;

import com.ryit.commons.web.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author ryit
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("member", code, args, null);
    }
}

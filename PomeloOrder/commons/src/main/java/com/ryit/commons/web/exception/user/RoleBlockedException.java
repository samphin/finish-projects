package com.ryit.commons.web.exception.user;

/**
 * 角色锁定异常类
 * 
 * @author ryit
 */
public class RoleBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public RoleBlockedException(String reason)
    {
        super("role.blocked", new Object[] { reason });
    }
}

package com.ryit.commons.web.exception.user;

/**
 * 验证码错误异常类
 * 
 * @author ryit
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("member.jcaptcha.error", null);
    }
}

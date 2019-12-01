package com.ryit.commons.web.exception.user;

/**
 * @author: zhangweixun
 * @Date: 2019/9/25 0025下午 9:31
 */
public class UserCreditCardException extends RuntimeException {

    private static final long serialVersionUID = -6736944294947154413L;

    public UserCreditCardException(String msg) {
        super(msg);
    }

    public UserCreditCardException() {
        super("身份证和姓名不匹配");
    }


}

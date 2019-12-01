package com.ryit.commons.web.exception.user;

/**
 * @author : 刘修
 * @Date : 2019/8/21 17:25
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -6736944294947154413L;

    public CustomException(String msg) {
        super(msg);
}

    public CustomException() {
        super();
    }

    public CustomException(String message, Throwable cause) {
        super(message,cause);
    }

    public CustomException(int errorCode) {
        super("errorCode:" + errorCode);
    }
}

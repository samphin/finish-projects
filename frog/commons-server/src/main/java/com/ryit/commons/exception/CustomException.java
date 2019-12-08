package com.ryit.commons.exception;

import com.ryit.commons.enums.SystemErrorEnum;

/**
 * @author : samphin
 * @since : 2019-9-28 15:00:20
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -6736944294947154413L;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException() {
        super();
    }

    private int errorCode;

    private String errorMsg;

    public CustomException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        errorMsg = message;
    }


    public CustomException(SystemErrorEnum errorEnum) {
        this(errorEnum.getErrorCode(), errorEnum.getErrorMsg());
    }


    public SystemErrorEnum getSystemErrorEnum() {
        return SystemErrorEnum.findByCode(errorCode);
    }
}

package com.ryit.commons.base.vo;

import com.ryit.commons.enums.SystemErrorEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.io.Serializable;

/**
 * 操作消息提醒
 * 系统统一返回对象
 *
 * @author samphin
 */
public class ResponseData<T> implements Serializable {

    /**
     * 成功消息提醒
     */
    private final static String SUCCESS_MESSAGE = "操作成功";

    /**
     * 失败消息提醒
     */
    private final static String FAILURE_MESSAGE = "操作失败";

    /**
     * 成功状态码
     */
    private final static Integer SUCCESS_CODE = HttpStatus.SC_OK;

    /**
     * 失败状态码
     */
    private final static Integer FAILURE_CODE = HttpStatus.SC_BAD_REQUEST;

    private T data;

    /**
     * 将态码
     */
    private Integer code;

    /**
     * 消息提醒
     * @param data
     * @return
     */
    private String msg;

    public ResponseData setData(T data) {
        this.data = data;
        return this;
    }

    public static ResponseData success(String msg) {
        ResponseData result = new ResponseData();
        result.setMsg(msg);
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static <T> ResponseData success(T data) {
        ResponseData result = new ResponseData();
        result.setMsg(SUCCESS_MESSAGE);
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    public static ResponseData success() {
        ResponseData result = new ResponseData();
        result.setMsg(SUCCESS_MESSAGE);
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static ResponseData failure(int code, String msg) {
        ResponseData result = new ResponseData();
        result.setCode(code);
        if (StringUtils.isEmpty(msg)) {
            msg = FAILURE_MESSAGE;
        }
        result.setMsg(msg);
        return result;
    }

    public static ResponseData failure(SystemErrorEnum systemErrorEnum) {
        ResponseData result = new ResponseData();
        result.setCode(systemErrorEnum.getErrorCode());
        result.setMsg(systemErrorEnum.getErrorMsg());
        return result;
    }

    public static ResponseData failure() {
        return failure("");
    }

    public static ResponseData failure(String msg) {
        ResponseData result = new ResponseData();
        result.setCode(FAILURE_CODE);
        if (StringUtils.isEmpty(msg)) {
            msg = FAILURE_MESSAGE;
        }
        result.setMsg(msg);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
}

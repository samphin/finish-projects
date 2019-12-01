package com.ryit.commons.base;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 系统统一出参对象
 *
 * @author samphin
 * @date 2019-9-30 15:43:35
 */
@Data
public class AjaxResult implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据对象
     */
    private Object data;

    /**
     * 成功状态码
     */
    private final static int SUCCESS_CODE = 1;

    /**
     * 失败状态码
     */
    private final static int ERROR_CODE = 0;

    /**
     * 成功消息提示
     */
    private final static String SUCCESS_MSG = "操作成功";

    /**
     * 失败消息提示
     */
    private final static String ERROR_MSG = "操作失败";

    /**
     * 初始化一个新创建的 Message 对象
     */
    public AjaxResult() {
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error() {
        return error(ERROR_CODE, ERROR_MSG);
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg) {
        return error(ERROR_CODE, msg);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg  内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg) {
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        if (StringUtils.isEmpty(msg)) {
            msg = ERROR_MSG;
        }
        result.setMsg(msg);

        return result;
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        AjaxResult result = new AjaxResult();
        result.setCode(SUCCESS_CODE);
        result.setMsg(msg);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param data
     * @return
     */
    public static AjaxResult success(Object data) {
        AjaxResult result = new AjaxResult();
        result.setCode(SUCCESS_CODE);
        result.setMsg(SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param msg
     * @param data
     * @return
     */
    public static AjaxResult success(String msg, Object data) {
        AjaxResult result = new AjaxResult();
        result.setCode(SUCCESS_CODE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        AjaxResult result = new AjaxResult();
        result.setCode(SUCCESS_CODE);
        result.setMsg(SUCCESS_MSG);
        return result;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    public static AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }
}

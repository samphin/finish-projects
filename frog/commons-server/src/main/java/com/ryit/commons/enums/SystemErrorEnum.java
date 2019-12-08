package com.ryit.commons.enums;

import com.ryit.commons.exception.CustomException;

/**
 * 系统错误信息枚举类
 *
 * @author samphin
 * @since 2019-9-28 14:58:49
 */
public enum SystemErrorEnum {

    //系统级统一错误信息
    ERROR_PARAM(300, "参数错误"),
    PLEASE_RETRY_AGAIN(0, "操作失败"),
    PLEASE_LOGIN(10000, "请登录"),
    USER_NO_EXIST(10001, "用户不存在"),
    LOGIN_PASSWORD_ERROR(10002, "用户密码错误"),
    EXIST_TELEPHONE_ERROR(10003, "手机号已注册"),
    VALIDATE_CODE_IS_EMPTY(10004, "验证码不能为空"),
    GET_VALIDATE_CODE(10005, "请先获取验证码"),
    VALIDATE_CODE_ERROR(10006, "验证码输入有误"),
    TOKEN_IS_EMPTY(10007, "token为空"),
    TOKEN_VERIFICATION(10008, "token认证失败"),
    TOKEN_OVERDUE(10009, "token已过期"),
    HYSTRIX_FALLBACK_ERROR(10010, "访问异常"),
    ILLEGAL_REQUEST_ERROR(10011, "非法请求"),
    ILLEGAL_LOGIN_ERROR(10012, "非法登录"),
    QUERY_LOGIN_INFO_ERROR(10013, "获取用户登录信息失败"),
    USER_REGISTER_ERROR(10014, "用户信息注册失败"),

    //文件系统
    FILE_UPLOAD_ERROR(20001, "文件上传失败"),
    FILE_READ_ERROR(20002, "文件查看失败"),
    FILE_DOWNLOAD_ERROR(20003, "文件下载失败"),

    //商品模块
    TROLLEY_ID_NOT_EMPTY(30001, "购物车ID不能为空"),
    GOODS_PRICE_SET_ERROR(30002, "商品今日价格设置失败"),
    GOODS_SKU_SAVE_ERROR(30003, "商品规格保存失败"),
    GOODS_TROLLEY_EMPTY_ERROR(30004, "购物车商品删除失败"),
    GOODS_SHAPE3_WIDTH_ERROR(30005, "查询横拉板宽度列表失败"),

    //订单模块
    ORDER_NO_PAY_LIST_ERROR(60001, "查询我的未支付订单列表失败"),
    ORDER_CERTIFICATE_ERROR(60002, "上传订单凭证失败"),
    ORDER_TURNOVER_ERROR(60003, "统计订单营业额失败"),
    ORDER_NUM_ERROR(60004, "统计用户订单数量失败"),
    ORDER_TOP_ERROR(60005, "统计用户订单数量排行榜失败"),
    ORDER_REFUND_ERROR(60006,"用户订单退款失败"),



    //用户模块
    QUERY_USER_ROLES_ERROR(90000, "获取用户角色信息失败"),
    USER_COMPANY_EMPTY_ERROR(90001, "用户公司信息不存在"),
    QUERY_USER_COMPANY_ERROR(90002, "获取用户公司信息失败");




    /**
     * 错误码
     */
    private final int errorCode;

    /**
     * 错误消息
     */
    private final String errorMsg;

    SystemErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public CustomException getCustomException() {
        return getCustomException(errorMsg);
    }

    /**
     * 返回输入的错误信息
     *
     * @param msg
     * @return
     */
    public CustomException getCustomException(String msg) {
        return new CustomException(errorCode, msg);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static SystemErrorEnum findByCode(int code) {
        for (SystemErrorEnum value : SystemErrorEnum.values()) {
            if (value.errorCode == (code)) {
                return value;
            }
        }
        return null;
    }
}

package com.bchengchat.common.utils;

public enum ResultCode {
    SUCCESS(200, "成功"),
    BUSY_NETWORK(400, "网络繁忙, 请稍后再试"),
    FAILURE(400, "失败"),
    LOGIN_NONE(301,"请先登录"),
    LOGIN_INVALID(302,"账户登录过期，请重新登录"),
    LOGIN_OUT(303,"账户异地登录，请重新登录"),
    PRARM_TYPE_ERROR(400, "参数类型错误"),
    PRARM_FORMAT_ERROR(400, "参数格式错误"),
    PRARM_CONTENT_TYPE_ERROR(400, "Content-Type 类型错误"),
    PRARM_ERROR(400, "参数错误"),
    HIKVISION_ERROR(10000, "海康异常"),

    ;
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

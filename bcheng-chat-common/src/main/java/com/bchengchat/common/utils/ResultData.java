package com.bchengchat.common.utils;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bchengchat.common.exception.GlobalException;
import com.bchengchat.common.exception.ValidateToolsException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;

/**
 * @Author LemonUnique
 * @Create 2020-11-25 17:58
 * @Description <p>通用ResultData类</p>
 */
@ApiModel(description = "请求结果类")
public class ResultData<T> implements Serializable {
    // 消息状态码
    @ApiModelProperty(value = "消息状态码", example = "200")
    private Integer code;
    // 消息
    @ApiModelProperty(value = "消息", example = "成功")
    private String message;
    // 返回数据
    @ApiModelProperty("返回数据")
    private T data;
    // 请求ID
    @ApiModelProperty("请求ID")
    private String requestId;

    public ResultData() {
        this.requestId = TraceIdUtil.get();
    }

    private ResultData(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.requestId = TraceIdUtil.get();
    }

    private ResultData(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
        this.requestId = TraceIdUtil.get();
    }

    private ResultData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.requestId = TraceIdUtil.get();
    }

    /**
     * 响应成功(带返回数据)
     *
     * @param data 返回数据
     * @return Result
     */
    public static <T> ResultData<T> success(T data) {
        return new ResultData<T>(ResultCode.SUCCESS, data);
    }

    /**
     * 成功带消息
     *
     * @param msg 消息
     * @param <T>
     * @return ResultData<T>
     */
    public static <T> ResultData<T> success(String msg) {
        return new ResultData<T>(ResultCode.SUCCESS.getCode(), msg, null);
    }

    /**
     * 响应成功
     *
     * @return ResultData<T>
     */
    public static <T> ResultData<T> success() {
        return new ResultData<T>(ResultCode.SUCCESS);
    }

    /**
     * 响应错误
     *
     * @param msg 消息
     * @param <T>
     * @return ResultData<T>
     */
    public static <T> ResultData<T> error(String msg) {
        return new ResultData<T>(ResultCode.FAILURE.getCode(), msg, null);
    }

    /**
     * @param code 状态码
     * @param msg  消息
     * @param <T>
     * @return ResultData<T>
     */
    public static <T> ResultData<T> error(Integer code, String msg) {
        return new ResultData<T>(code, msg, null);
    }

    /**
     * 响应错误
     *
     * @param <T>
     * @return ResultData<T>
     */
    public static <T> ResultData<T> error() {
        return new ResultData<T>(ResultCode.FAILURE, null);
    }


    public static ResultData<Object> error(BindingResult result) {
        Object target = result.getTarget();
        if (target == null) {
            return error(result.getFieldError());
        }
        FieldError fieldError = result.getFieldError();
        try {
            return Optional.ofNullable(fieldError).map(error -> new ResultData<Object>() {{
                setCode(ResultCode.FAILURE.getCode());
                setMessage(formatMessage(target.getClass(), fieldError));
            }}).orElseThrow(GlobalException::new);
        } catch (Exception e) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }

    private static String formatMessage(Class<?> targetClass, FieldError error) {
        Object fieldName = Optional.ofNullable(
                getFieldStr(targetClass, error.getField())
        ).orElse(error.getField() + StrPool.COLON + StrPool.C_SPACE);
        return fieldName + formattingMessage(error.getDefaultMessage());
    }

    public static ResultData<Object> error(FieldError fieldError) {
        try {
            return Optional.ofNullable(fieldError).map(error -> new ResultData<>(
                    ResultCode.FAILURE.getCode(),
                    new StringJoiner(StrPool.COLON + StrPool.C_SPACE).add(
                            error.getField()
                    ).add(formattingMessage(error.getDefaultMessage())).toString(),
                    null
            )).orElseGet(() -> bind(ResultCode.PRARM_ERROR));
        } catch (Exception e) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }

    public static ResultData<Object> error(ValidateToolsException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        try {
            return constraintViolations.stream().findFirst().map(constraintViolation -> {
                String field = constraintViolation.getPropertyPath().toString();
                Class<?> targetClass = constraintViolation.getLeafBean().getClass();
                String message = constraintViolation.getMessage();
                Object fieldName = Optional.ofNullable(
                        getFieldStr(targetClass, field)
                ).orElse(field + StrPool.COLON + StrPool.C_SPACE);
                return new ResultData<>(
                        ResultCode.FAILURE.getCode(),
                        fieldName + (formattingMessage(message)),
                        null
                );
            }).orElseGet(() -> bind(ResultCode.PRARM_ERROR));
        } catch (Exception e) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }

    public static ResultData<Object> error(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        try {
            return constraintViolations.stream().findFirst().map(constraintViolation -> new ResultData<Object>(){{
                setCode( ResultCode.FAILURE.getCode());
                setMessage(new StringJoiner(StrPool.COLON + StrPool.C_SPACE).add(
                        ReUtil.delAll("^[a-zA-Z0-9]*\\.", constraintViolation.getPropertyPath().toString())
                ).add(constraintViolation.getMessage()).toString());
            }}).orElseThrow(GlobalException::new);
        } catch (Exception e) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }

    private static Object getFieldStr(Class<?> targetClass, String fieldStr) {
        if (StrUtil.isBlank(fieldStr)) {
            return null;
        }
        Field field = ReflectUtil.getField(targetClass, fieldStr);
        if (field == null) {
            return null;
        }
        Object value = AnnotationUtil.getAnnotationValue(field, ApiModelProperty.class);
        if (value != null) {
            if (StrUtil.isBlank(value.toString())) {
                value = null;
            }
        }
        return value;
    }

    private static String formattingMessage(String message) {
        if (message == null) {
            return null;
        }
        return message.replaceAll(StrUtil.NULL, "空");
    }



    /**
     * 根据 MySQL影响行数
     *
     * @param rows
     * @param success 成功参数
     * @param error   失败参数
     * @param <T>
     * @return ResultData<T>
     */
    public static <T> ResultData<T> sqlResult(Integer rows, ResultCode success, ResultCode error) {
        if (rows > 0) {
            return ResultData.bind(success);
        } else {
            return ResultData.bind(error);
        }
    }

    /**
     * 根据 MySQL影响行数
     *
     * @param rows 影响行数
     * @param <T>
     * @return ResultData.success(); || ResultData.error();
     */
    public static <T> ResultData<T> sqlResult(Integer rows) {
        return sqlResult(rows, ResultCode.SUCCESS, ResultCode.FAILURE);
    }

    /**
     * 根据 result
     *
     * @param result
     * @param <T>
     * @return ResultData.success(); || ResultData.error();
     */
    public static <T> ResultData<T> sqlResult(boolean result) {
        if (result) {
            return success();
        } else {
            return error();
        }
    }

    /**
     * 构建
     *
     * @param resultCode
     * @param <T>
     * @return ResultData<T>
     */
    public static <T> ResultData<T> bind(ResultCode resultCode) {
        return new ResultData<T>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> ResultData<T> exception(GlobalException e) {
        return error(e.getCode(), e.getMessage());
    }


    public Integer getCode() {
        return code;
    }

    public ResultData<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultData<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultData<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 判断 接口状态是否是 200
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.getCode())
                && ResultCode.SUCCESS.getMessage().equals(this.getMessage());
    }


    /**
     * 如果成功 调用 提供的 Consumer
     * 案例：
     * ResultData.success().ifSuccess(o -> {
     * // code == 200 那么调用此方法
     * });
     *
     * @param consumer
     */
    @JsonIgnore
    public void ifSuccess(Consumer<? super T> consumer) {
        if (isSuccess()) {
            consumer.accept(this.getData());
        }
    }

    @JsonIgnore
    public String toJsonString() {
        return JSONUtil.toJsonStr(this);
    }

    @Override
    public String toString() {
        return "ResultData{" + "code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
    }
}

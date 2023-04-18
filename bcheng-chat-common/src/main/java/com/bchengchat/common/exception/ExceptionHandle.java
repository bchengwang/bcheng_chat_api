package com.bchengchat.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.bchengchat.common.utils.ResultCode;
import com.bchengchat.common.utils.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = NotLoginException.class)
    public ResultData<Object> getNotLoginException(NotLoginException e) {
        log.error(" <<<===", e);
        if (NotLoginException.NOT_TOKEN.equals(e.getType())) {
            return ResultData.bind(ResultCode.LOGIN_NONE);
        }
        if (NotLoginException.INVALID_TOKEN.equals(e.getType()) ||
                (NotLoginException.TOKEN_TIMEOUT.equals(e.getType()))) {
            return ResultData.bind(ResultCode.LOGIN_INVALID);
        }
        if (NotLoginException.BE_REPLACED.equals(e.getType()) ||
                (NotLoginException.KICK_OUT.equals(e.getType()))) {
            return ResultData.bind(ResultCode.LOGIN_OUT);
        }
        return ResultData.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public ResultData<Object> httpMessageConversionException(HttpMessageConversionException e) {
        this.printStackTrace(e);
        return ResultData.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResultData<Object> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        this.printStackTrace(e);
        return ResultData.bind(ResultCode.PRARM_CONTENT_TYPE_ERROR);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultData<Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        this.printStackTrace(e);
        return ResultData.bind(ResultCode.PRARM_FORMAT_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResultData<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        this.printStackTrace(e);
        return ResultData.bind(ResultCode.PRARM_TYPE_ERROR);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultData<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        this.printStackTrace(e);
        return ResultData.error(e.getMessage());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResultData<Object> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        this.printStackTrace(e);
        return ResultData.error(e.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultData<Object> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        this.printStackTrace(e);
        return ResultData.bind(ResultCode.PRARM_ERROR);
    }

    @ExceptionHandler(value = GlobalException.class)
    public ResultData<Object> getGlobalException(GlobalException e) {
        printStackTrace(e);
        return ResultData.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResultData<Object> getException(Exception e) {
        printStackTrace(e);
        return ResultData.error("服务繁忙");
    }

    /**
     * 参数校验 异常信息处理
     *
     * @param e BindException
     * @return ResultData
     */
    @ExceptionHandler(value = BindException.class)
    public ResultData<Object> bindException(BindException e) {
        log.warn("BindException", e);
        try {
            return ResultData.error(e.getBindingResult());
        } catch (Exception ig) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }

    /**
     * Controller参数校验 异常信息处理
     *
     * @param e CustomConstraintViolationException
     * @return ResultData
     */
    @ExceptionHandler(value = ValidateToolsException.class)
    public ResultData<Object> constraintViolationException(ValidateToolsException e) {
        log.warn("CustomValidationException", e);
        try {
            return ResultData.error(e);
        } catch (Exception ig) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }


    /**
     * Controller参数校验 异常信息处理
     *
     * @param e ConstraintViolationException
     * @return ResultData
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultData<Object> constraintViolationException(ConstraintViolationException e) {
        log.warn("ConstraintViolationException", e);
        try {
            return ResultData.error(e);
        } catch (Exception ig) {
            return ResultData.bind(ResultCode.PRARM_ERROR);
        }
    }

    private void printStackTrace(Exception e) {
        e.printStackTrace();
    }
}
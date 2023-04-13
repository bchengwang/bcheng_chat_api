package com.bcheng.chat.exception;


import com.bcheng.chat.utils.ResultCode;
import com.bcheng.chat.utils.ResultData;

/**
 * @Author Bcheng
 * @Create 2021/4/16 上午 10:45
 * @Description <p>GlobalException</p>
 */
public class GlobalException extends RuntimeException {
    // 消息状态码
    private Integer code = ResultCode.FAILURE.getCode();
    // 消息
    private String message = ResultCode.FAILURE.getMessage();


    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
        this.setMessage(message);
    }

    public GlobalException(String message, Integer code) {
        super(message);
        this.setCode(code);
        this.setMessage(message);
    }

    public GlobalException(ResultCode status) {
        super(status.getMessage());
        this.setCode(status.getCode());
        this.setMessage(status.getMessage());
    }

    public <T> GlobalException(ResultData<T> result) {
        super(result.getMessage());
        this.setCode(result.getCode());
        this.setMessage(result.getMessage());
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

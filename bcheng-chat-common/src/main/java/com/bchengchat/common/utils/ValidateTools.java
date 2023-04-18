package com.bchengchat.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.bchengchat.common.exception.ValidateToolsException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author Bcheng
 * @Create 2021/7/19
 * @Description <p>手动调用校验框架</p>
 */
@Component
public class ValidateTools {
    @Resource
    private Validator validator;

    /**
     * 没有分组的校验方法
     *
     * @param t   校验的实体类
     * @param <T>
     */
    public <T> void validate(T t) {
        validate(t, new Class<?>[0]);
    }

    /**
     * 有分组的校验方法
     *
     * @param t      校验的实体类
     * @param groups 检验分组
     * @param <T>
     */
    public <T> void validate(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = getConstraintViolation(t, groups);
        if (CollUtil.isNotEmpty(validate)) {
            throw new ValidateToolsException(validate);
        }
    }

    /**
     * 校验获取校验结果
     *
     * @param t   校验的实体类
     * @param <T>
     * @return 校验结果
     */
    public <T> Set<ConstraintViolation<T>> getConstraintViolation(T t) {
        return getConstraintViolation(t, new Class<?>[0]);
    }

    /**
     * 校验获取校验结果所有错误
     *
     * @param t      校验的实体类
     * @param groups 检验分组
     * @param <T>
     * @return 校验结果
     */
    public <T> Set<ConstraintViolation<T>> getConstraintViolation(T t, Class<?>... groups) {
        return validator.validate(t, groups);
    }
}

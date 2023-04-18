package com.bchengchat.common.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Author Bcheng
 * @Create 2021/9/6
 * @Description <p>CustomConstraintViolationException</p>
 */
public class ValidateToolsException extends ConstraintViolationException {
    public ValidateToolsException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }

    public ValidateToolsException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}

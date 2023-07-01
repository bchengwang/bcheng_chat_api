package com.bchengchat.common.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Bcheng
 * @Create 2021/7/30
 * @Description <p>GlobalJava8DateHandler</p>
 */
@ControllerAdvice
public class GlobalJava8DateHandler {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StrUtil.isNotEmpty(text)) {
                    setValue(LocalDate.parse(text, DatePattern.NORM_DATE_FORMATTER));
                }
            }
        });
        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StrUtil.isNotEmpty(text)) {
                    setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
                }
            }
        });
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StrUtil.isNotEmpty(text)) {
                    setValue(LocalDateTime.parse(text, DatePattern.NORM_DATETIME_FORMATTER));
                }
            }
        });
    }
}

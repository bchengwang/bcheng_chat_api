package com.bcheng.chat.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;

/**
 * @Author Bcheng
 * @Create 2021/7/13 下午 6:34
 * @Description <p>快速校验</p>
 */
@Configuration
public class ValidatedConfig {
    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .defaultLocale(Locale.CHINA)
                .failFast(true)
                .buildValidatorFactory();
        return factory.getValidator();
    }
}

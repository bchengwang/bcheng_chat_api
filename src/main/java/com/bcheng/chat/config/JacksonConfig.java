package com.bcheng.chat.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Author 北橙
 * @Create 2022/9/29
 * @Description Json 序列化问题
 * @Version 1.0
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));

            //全局转化long、BigInteger类型为String
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        };
    }
}

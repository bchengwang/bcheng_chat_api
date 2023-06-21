package com.bchengchat.common.rabbitmq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * @Author 北橙
 * @Create 2023/4/20
 * @Description 延时消息
 * @Version 1.0
 */
@Builder
@Getter
@Setter
public class DelayMessage<T> extends Message<T> {
    /**
     * 延时时间
     */
    private Long delayTime;

    /**
     * 延时时间单位
     */
    private TimeUnit delayTimeUnit;
}

package com.bchengchat.common.rabbitmq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author 北橙
 * @Create 2023/4/20
 * @Description 消息主体
 * @Version 1.0
 */
@Builder
@Getter
@Setter
public class Message<T> {

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息承载的业务数据
     */
    private T body;

    /**
     * 交换机名称
     */
    private String exchangeName;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 消息状态
     *
     * @see MessageStatus
     */
    private MessageStatus status;

    /**
     * 重试次数
     */
    private int retryTimes;

    /**
     * 下一次重试时间
     */
    private LocalDateTime nextRetryDateTime;
}

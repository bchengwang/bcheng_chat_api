package com.bchengchat.common.rabbitmq.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @Author 北橙
 * @Create 2023/4/21
 * @Description 确认回调事件
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class ConfirmCallbackEvent {
    /**
     * correlation data for the callback
     */
    private CorrelationData correlationData;

    /**
     * true for ack, false for nack
     */
    private boolean ack;

    /**
     * An optional cause, for nack, when available, otherwise null.
     */
    private String cause;
}

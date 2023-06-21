package com.bchengchat.common.rabbitmq;

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Author 北橙
 * @Create 2023/4/21
 * @Description RabbitTemplate 增强类
 * @Version 1.0
 */
public interface RabbitEnhanceTemplate extends BeanPostProcessor {

    /**
     * 确认消息
     *
     * @param messageId 消息id
     */
    void confirmMessage(String messageId);

    /**
     * 重试消息
     *
     * @param messageId 消息id
     */
    void retryMessage(String messageId);

    /**
     * 发送延时消息
     *
     * @param delayMessage 延迟消息主体
     * @param <T>          数据主体类型
     */
    <T> void commit(DelayMessage<T> delayMessage);

    /**
     * 发送普通消息
     *
     * @param message 普通消息主体
     * @param <T>     数据主体类型
     */
    <T> void commit(Message<T> message);
}

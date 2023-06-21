package com.bchengchat.common.rabbitmq;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.bchengchat.common.rabbitmq.event.ConfirmCallbackEvent;
import com.bchengchat.common.rabbitmq.event.ReturnsCallbackEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * @Author 北橙
 * @Create 2023/4/20
 * @Description RabbitTemplate增强类
 * @Version 1.0
 */
@Slf4j
@Component
public abstract class RabbitEnhanceTemplateImpl implements RabbitEnhanceTemplate {
    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void confirmMessage(String messageId) {

    }

    @Override
    public void retryMessage(String messageId) {

    }

    @Override
    public <T> void commit(DelayMessage<T> delayMessage) {
        Assert.notEmpty(delayMessage.getMessageId());
        Assert.notEmpty(delayMessage.getExchangeName());
        Assert.notEmpty(delayMessage.getRoutingKey());
        Assert.notNull(delayMessage.getBody());
        Assert.notNull(delayMessage.getDelayTimeUnit());
        Assert.notNull(delayMessage.getDelayTime());
        Assert.notNull(delayMessage.getNextRetryDateTime());
        //消息投递到MQ
        rabbitTemplate.convertAndSend(delayMessage.getExchangeName(), delayMessage.getRoutingKey(), delayMessage.getBody(), processor -> {
            MessageProperties messageProperties = processor.getMessageProperties();
            long millis = delayMessage.getDelayTimeUnit().toMillis(delayMessage.getDelayTime());
            messageProperties.setHeader(MessageProperties.X_DELAY, millis);
            return processor;
        }, new CorrelationData(delayMessage.getMessageId()));
    }

    @Override
    public <T> void commit(Message<T> message) {
        Assert.notEmpty(message.getMessageId());
        Assert.notEmpty(message.getExchangeName());
        Assert.notEmpty(message.getRoutingKey());
        Assert.notNull(message.getBody());
        Assert.notNull(message.getNextRetryDateTime());
        CorrelationData correlationData = new CorrelationData(message.getMessageId());
        //消息投递到MQ
        rabbitTemplate.convertAndSend(message.getExchangeName(), message.getRoutingKey(), message.getBody(), correlationData);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, @Nonnull String beanName) throws BeansException {
        //增强RabbitTemplate
        if (RabbitTemplate.class.equals(bean.getClass())) {
            log.debug(" <<<=== 增强RabbitTemplate 类名: {} BeanName: {}", bean.getClass(), beanName);
            RabbitTemplate template = (RabbitTemplate) bean;
            template.setConfirmCallback((correlationData, ack, cause) -> {
                log.debug(" <<<=== 消息确认回调 CorrelationData {} : Ack : {} cause : {}", correlationData, ack, cause);
                applicationContext.publishEvent(new ConfirmCallbackEvent(correlationData, ack, cause));
            });
            template.setReturnsCallback(returned -> {
                log.debug(" <<<=== 消息返回回调 ReturnedMessage {}", JSONUtil.formatJsonStr(JSONUtil.toJsonStr(returned)));
                applicationContext.publishEvent(new ReturnsCallbackEvent(returned));
            });
            return template;
        }
        return bean;
    }
}

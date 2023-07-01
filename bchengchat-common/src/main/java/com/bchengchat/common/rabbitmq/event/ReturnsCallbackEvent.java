package com.bchengchat.common.rabbitmq.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Delegate;
import org.springframework.amqp.core.ReturnedMessage;

/**
 * @Author 北橙
 * @Create 2023/4/21
 * @Description 回调消息事件
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class ReturnsCallbackEvent {
    @Delegate
    private ReturnedMessage returned;
}

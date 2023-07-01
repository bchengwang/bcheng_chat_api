package com.bchengchat.common.message;

import lombok.Data;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description 基本信息类型
 * @Version 1.0
 */
@Data
public class BaseMessage<T> {

    /**
     * 消息类型 详见 {@link MessageType}
     */
    private MessageType messageType;

    /**
     * 消息主体
     */
    private T body;

    /**
     * 发送者
     */
    private Long fromUserId;

    /**
     * 接收者
     */
    private Long toUserId;
}

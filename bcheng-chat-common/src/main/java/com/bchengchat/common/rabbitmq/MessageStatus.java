package com.bchengchat.common.rabbitmq;

import lombok.AllArgsConstructor;

/**
 * @Author 北橙
 * @Create 2023/4/20
 * @Description 消息状态
 * @Version 1.0
 */
@AllArgsConstructor
public enum MessageStatus {
    /**
     * 发送中
     */
    SENDING("发送中"),

    /**
     * 发送成功
     */
    SUCCESS( "发送成功"),

    /**
     * 发送失败
     */
    FAIL("发送失败");

    private String description;
}

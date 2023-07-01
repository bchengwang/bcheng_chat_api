package com.bchengchat.common.message.body;

import lombok.Data;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description 文本消息
 * @Version 1.0
 */
@Data
public class TextMessageBody {
    /**
     * 消息内容
     */
    private String text;
}

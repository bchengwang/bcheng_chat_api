package com.bchengchat.common.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description 文本消息
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextMessage extends BaseMessage<String> {
}

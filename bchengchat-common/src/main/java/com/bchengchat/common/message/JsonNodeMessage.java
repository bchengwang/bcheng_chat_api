package com.bchengchat.common.message;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description TODO
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class JsonNodeMessage extends BaseMessage<JsonNode> {
}

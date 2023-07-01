package com.bchengchat.app.service;

import com.bchengchat.common.message.JsonNodeMessage;
import com.bchengchat.common.utils.ResultData;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description TODO
 * @Version 1.0
 */
public interface MessageService {
    ResultData<Object> commitMessage(JsonNodeMessage message);
}

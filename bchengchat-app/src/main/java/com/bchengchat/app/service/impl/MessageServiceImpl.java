package com.bchengchat.app.service.impl;

import com.bchengchat.app.service.MessageService;
import com.bchengchat.common.message.JsonNodeMessage;
import com.bchengchat.common.utils.ResultData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description TODO
 * @Version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public ResultData<Object> commitMessage(JsonNodeMessage message) {
        switch (message.getMessageType()){
            case TEXT_MESSAGE:
                message.build(objectMapper);

        }
        return ResultData.success();
    }
}

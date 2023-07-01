package com.bchengchat.app.controller;

import com.bchengchat.app.service.MessageService;
import com.bchengchat.common.message.JsonNodeMessage;
import com.bchengchat.common.utils.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 北橙
 * @Create 2023/7/1
 * @Description 消息相关接口
 * @Version 1.0
 */
@Tag(name = "消息相关接口", description = "消息相关接口")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Operation(summary = "提交消息", description = "提交消息")
    @PostMapping("/commit")
    public ResultData<Object> commitMessage(@RequestBody JsonNodeMessage message) {
        return messageService.commitMessage(message);
    }
}

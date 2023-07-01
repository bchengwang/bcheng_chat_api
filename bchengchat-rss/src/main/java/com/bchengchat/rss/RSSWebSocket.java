package com.bchengchat.rss;


import com.bchengchat.rss.service.RSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import javax.annotation.Resource;

/**
 * @Author 北橙
 * @Create 2022/11/2
 * @Description 前后端交互的类实现消息的接收推送
 * @Version 1.0
 */
@Slf4j
@ServerEndpoint(value = "/ws/rss/{Token}", port = "19527")
@Component
public class RSSWebSocket {


    @Resource
    private RSSService rssService;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(final Session session, @PathVariable("Token") String token) {
        rssService.register(token, session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(final Session session) {
        rssService.close(session);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(final String message, final Session session) {
        rssService.message(message, session);
    }

    /**
     *
     * @param session 订阅session
     * @param error 错误信息
     */
    @OnError
    public void onError(final Session session, final Throwable error) {
        rssService.error(session, error);
    }
}

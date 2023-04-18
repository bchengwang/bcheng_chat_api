package com.bchengchat.rss.service;

import org.yeauty.pojo.Session;

/**
 * @Author 北橙
 * @Create 2023/4/18
 * @Description 订阅逻辑类
 * @Version 1.0
 */
public interface RSSService {

    /**
     * 注册订阅
     * @param token 订阅token
     * @param session 订阅session连接
     */
    void register(String token, Session session);

    /**
     * 关闭订阅
     * @param session 订阅session连接
     */
    void close(Session session);

    /**
     * 接收消息
     * @param message 消息内容
     * @param session 订阅session连接
     */
    void message(String message, Session session);

    /**
     * 订阅错误
     * @param session 订阅session连接
     * @param error 错误信息
     */
    void error(Session session, Throwable error);
}

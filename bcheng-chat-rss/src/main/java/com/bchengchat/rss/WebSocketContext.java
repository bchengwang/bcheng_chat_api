package com.bchengchat.rss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.yeauty.pojo.Session;

/**
 * @Author 北橙
 * @Create 2022/11/3
 * @Description WebSocketContext 上下文
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public class WebSocketContext {
    private Session session;
}

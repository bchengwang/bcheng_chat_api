package com.bchengchat.rss;

import org.yeauty.pojo.Session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 北橙
 * @Create 2022/11/2
 * @Description WebSocketContext上下文工具
 * @Version 1.0
 */
public class WebSocketContextTools {

    public final static Map<String, WebSocketContext> WEB_SOCKET_CONTEXT_MAP = new ConcurrentHashMap<>();

    public static WebSocketContext removeWebSocketContext(String sessionId) {
        return WEB_SOCKET_CONTEXT_MAP.remove(sessionId);
    }

    public static boolean containsWebSocketContextKey(String sessionId) {
        return WEB_SOCKET_CONTEXT_MAP.containsKey(sessionId);
    }

    public static Optional<WebSocketContext> getWebSocketContext(String sessionId) {
        return Optional.ofNullable(WEB_SOCKET_CONTEXT_MAP.get(sessionId));
    }

    public static void register(Session session) {
        WebSocketContext webSocketContext = new WebSocketContext(session);
        String sessionId = session.id().asLongText();
        if (!containsWebSocketContextKey(sessionId)) {
            WEB_SOCKET_CONTEXT_MAP.put(sessionId, webSocketContext);
        }
    }
}

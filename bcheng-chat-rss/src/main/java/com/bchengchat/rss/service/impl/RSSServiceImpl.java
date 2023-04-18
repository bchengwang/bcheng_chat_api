package com.bchengchat.rss.service.impl;

import com.bchengchat.rss.service.RSSService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.yeauty.pojo.Session;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 北橙
 * @Create 2023/4/18
 * @Description 订阅逻辑类
 * @Version 1.0
 */
public class RSSServiceImpl implements RSSService {

    /**
     * 记录当前在线网页数量
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void register(String token, Session session) {
        increment();
        String sessionId = session.id().asLongText();
        RBucket<String> sessionIdBucket = redissonClient.getBucket(token);
        sessionIdBucket.set(sessionId);
        RBucket<String> bucket = redissonClient.getBucket(sessionId);
        bucket.set(token);
    }

    @Override
    public void close(Session session) {
        decrement();

    }

    @Override
    public void message(String message, Session session) {

    }

    @Override
    public void error(Session session, Throwable error) {

    }

    private int decrement() {
        return ONLINE_COUNT.decrementAndGet();
    }

    private int increment() {
        return ONLINE_COUNT.incrementAndGet();
    }
}

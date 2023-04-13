package com.bcheng.chat.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;

/**
 * @Author 北橙
 * @Create 2022/10/9
 * @Description TraceIdUtil
 * @Version 1.0
 */
public final class TraceIdUtil {

    private static final String TRACE_ID_KEY = "TraceId";

    private TraceIdUtil() {
    }

    public static void putIfAbsent() {
        if (StrUtil.isBlank(get())) {
            put(IdUtil.objectId());
        }
    }

    public static void remove() {
        if (get() != null) {
            MDC.remove(TRACE_ID_KEY);
        }
    }

    public static String get() {
        return MDC.get(TRACE_ID_KEY);
    }

    public static void put(String traceId) {
        MDC.put(TRACE_ID_KEY, traceId);
    }
}

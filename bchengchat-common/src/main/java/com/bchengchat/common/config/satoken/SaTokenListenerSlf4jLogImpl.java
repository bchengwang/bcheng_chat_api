package com.bchengchat.common.config.satoken;

import cn.dev33.satoken.listener.SaTokenListenerDefaultImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author 北橙
 * @Create 2022/9/29
 * @Description SaTokenSlf4jLog实现
 * @Version 1.0
 */
@Slf4j
@Component
public class SaTokenListenerSlf4jLogImpl extends SaTokenListenerDefaultImpl {

    @Override
    public void println(String str) {
        log.debug(" <<<=== SaToken {}", str);
    }
}

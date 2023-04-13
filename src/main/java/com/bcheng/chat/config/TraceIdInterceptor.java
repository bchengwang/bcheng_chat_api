package com.bcheng.chat.config;


import com.bcheng.chat.utils.TraceIdUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 北橙
 * @Create 2022/10/9
 * @Description TraceIdInterceptor
 * @Version 1.0
 */
@Configuration
public class TraceIdInterceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AsyncHandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Object handler) throws Exception {
                TraceIdUtil.putIfAbsent();//生成trace id放入MDC中
                return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
            }

            @Override
            public void afterCompletion(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Object handler, Exception ex) throws Exception {
                TraceIdUtil.remove();//移除MDC中的trace id
                AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
            }
        });
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

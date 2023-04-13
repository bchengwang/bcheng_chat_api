package com.bcheng.chat;

import cn.hutool.extra.spring.EnableSpringUtil;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.yeauty.annotation.EnableWebSocket;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableSpringUtil
@EnableAdminServer
@EnableScheduling
@MapperScan("com.bcheng.chat.mapper")
@SpringBootApplication
@EnableWebSocket
@EnableAsync
public class MainApplication implements ApplicationListener<WebServerInitializedEvent> {

    @Resource
    private AdminServerProperties adminServerProperties;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        WebServer server = event.getWebServer();
        WebServerApplicationContext context = event.getApplicationContext();
        Environment env = context.getEnvironment();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int port = server.getPort();
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null) {
            contextPath = "";
        }
        String adminServerContextPath = adminServerProperties.getContextPath();
        log.info("\n|---------------------------------------------------------|\n" +
                "|\tApplication is running! Access address:\n" +
                "|\tLocal:\t\thttp://localhost:{}{}" +
                "\n|\tExternal:\thttp://{}:{}{}" +
                "\n|\tLocalDoc:\thttp://{}:{}/doc.html" +
                "\n|\tLocalDoc:\thttp://{}:{}/swagger-ui.html" +
                "\n|\tDeveloper:\thttp://{}:{}{}/login" +
                "\n|---------------------------------------------------------|\n", port, contextPath, ip, port, contextPath,ip, port,ip, port, ip, port, adminServerContextPath);
    }
}

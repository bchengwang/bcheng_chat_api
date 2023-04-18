package com.bchengchat.monitor.config;

import cn.hutool.core.util.StrUtil;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;

/**
 * @Author 北橙
 * @Create 2022/9/28
 * @Description Admin监控安全配置
 * @Version 1.0
 */
@Configuration
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AdminServerProperties adminServerProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String adminContextPath = adminServerProperties.getContextPath();
        if (StrUtil.isEmpty(adminContextPath)) {
            throw new NullPointerException("spring.boot.admin.context-path 不能为空");
        }
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");
        http.authorizeRequests()
                //1.配置所有静态资源和登录页可以公开访问
                .antMatchers(adminContextPath + "/assets/**")
                .permitAll()
                .antMatchers(adminContextPath + "/login")
                .permitAll()
                .antMatchers(adminContextPath + "/**", "/actuator/**", "/druid/**")
                .authenticated()
                .and()
                //2.配置登录和登出路径
                .formLogin()
                .loginPage(adminContextPath + "/login")
                .successHandler(successHandler)
                .and()
                .logout()
                .logoutUrl(adminContextPath + "/logout")
                .and()
                //3.开启http basic支持，admin-client注册时需要使用
                .httpBasic()
                .and()
                .csrf()
                .disable();

    }
}

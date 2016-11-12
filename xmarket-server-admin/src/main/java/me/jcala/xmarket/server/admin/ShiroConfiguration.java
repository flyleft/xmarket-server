package me.jcala.xmarket.server.admin;

import me.jcala.xmarket.server.admin.shiro.MongoReam;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {

    /**
     * admin cookie的定义
     */
    @Bean
    public SimpleCookie adminCookie() {
        SimpleCookie cookie = new SimpleCookie("admin_cookie");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24*3600*30);
        return cookie;
    }

    /**
     * 会话管理器
     */
    @Bean
    public SessionManager sessionManager(){
        DefaultSessionManager sessionManager=new DefaultSessionManager();
        sessionManager.setGlobalSessionTimeout(3600);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(new MongoReam());
        return securityManager;
    }
    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ShiroFilter());
        registration.addUrlPatterns("/admin/*","static/*");
        registration.setName("shiro-admin-filter");
        registration.setOrder(1);
        return registration;
    }

}

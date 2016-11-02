package me.jcala.xmarket.server.conf;

import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24*3600*30);
        return cookie;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ShiroFilter());
        registration.addUrlPatterns("/admin/*");
        registration.setName("shiro-admin-filter");
        registration.setOrder(1);
        return registration;
    }

}

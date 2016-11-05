package me.jcala.xmarket.server.conf;

import me.jcala.xmarket.server.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMavConfig extends WebMvcConfigurerAdapter {

    private TokenInterceptor tokenInterceptor;

    @Autowired
    public WebMavConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/auth")
                .excludePathPatterns("/api/v1/users/school_list")
                .excludePathPatterns("/api/v1/users/register");
    }
}

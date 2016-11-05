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
        //为了测试方便，先去除token拦截
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/api/v1/users/**")//拦截所有路径
//                .excludePathPatterns("/api/v1/users/school_list")//排除注册时获取学校列表的路径
//                .excludePathPatterns("/api/v1/users/register");//排除注册路径
    }
}

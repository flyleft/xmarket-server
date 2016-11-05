package me.jcala.xmarket.server.interceptor;

import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.utils.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 用于验证JWT Token
 */
@Service
public class TokenInterceptor implements HandlerInterceptor {

    private ApplicationInfo info;

    private final String tokenIllegal="{\"code\":104,\"msg\":\"token不合法\",\"data\":null}";

    @Autowired
    public TokenInterceptor(ApplicationInfo info) {
        this.info = info;
    }

    public TokenInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String jwt=request.getHeader("x-access-token");
         if (!CustomValidator.hasEmpty(jwt) && CustomValidator.JwtVerify(info.getJwtKey(),jwt)){
             return true;
         }else {
             response.setContentType("application/json;charset=UTF-8");
             response.setStatus(401);
             response.getWriter().write(tokenIllegal);
             return false;
         }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
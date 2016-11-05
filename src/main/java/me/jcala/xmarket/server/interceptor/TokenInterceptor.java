package me.jcala.xmarket.server.interceptor;

import me.jcala.xmarket.server.service.inter.TokenService;
import me.jcala.xmarket.server.utils.FieldValidator;
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

    private TokenService tokenService;

    @Autowired
    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public TokenInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String jwt=request.getHeader("x-access-token");
         if (FieldValidator.hasEmpty(jwt)){
             return false;
         }
        return tokenService.JwtVerify(jwt);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
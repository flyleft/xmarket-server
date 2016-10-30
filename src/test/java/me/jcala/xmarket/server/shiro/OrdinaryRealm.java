package me.jcala.xmarket.server.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class OrdinaryRealm implements Realm {
    @Override
    public String getName() {
        return "OrdinaryRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
           String username=(String) token.getPrincipal();//得到用户名
           String password=new String((char[])token.getCredentials());//得到密码
           if (!"jcala".equals(username)){
               throw new UnknownAccountException("该用户不存在");
           }
           if (!"123".equals(password)){
               throw new IncorrectCredentialsException("密码错误");
           }
         return new SimpleAuthenticationInfo(username,password,getName());
    }
}

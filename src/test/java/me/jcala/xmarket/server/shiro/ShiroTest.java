package me.jcala.xmarket.server.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class ShiroTest {
    @Test
    public void simple (){
        //用ini文件初始化SecurityManager工厂
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro.ini");
        //得到SecurityManager实例
        SecurityManager securityManager=factory.getInstance();
        //将SecurityManager实例绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        //得到Subject主体
        Subject subject=SecurityUtils.getSubject();
        //设置登录的Token
        UsernamePasswordToken token=new UsernamePasswordToken("jcala","123");
        try {
            //用Token验证身份
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println("用户已经登录?"+subject.isAuthenticated());
        //登出
        subject.logout();
        System.out.println("用户已经登录?"+subject.isAuthenticated());

    }
}

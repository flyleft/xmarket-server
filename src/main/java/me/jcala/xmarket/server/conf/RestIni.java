package me.jcala.xmarket.server.conf;

/**
 * 关于rest服务器接收和返回信息的配置
 */
public interface RestIni {
    int success=1;
    int fail=0;
    String loginPassErr="密码错误";
    String loginUmErr="该用户不存在";
    String RegisterPhoneErr="该手机号已被注册";
    String RegisterUmErr="该用户已经存在";
}

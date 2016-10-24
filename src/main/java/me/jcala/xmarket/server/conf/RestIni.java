package me.jcala.xmarket.server.conf;

/**
 * 关于rest服务器接收和返回信息的配置
 */
public interface RestIni {
    int success=1;
    int fail=0;
    int serverErr=-1;
    String serverErrMsg="服务器出现异常";

    String loginPassErr="密码错误";
    String loginUmErr="该用户不存在";

    String RegisterPhoneExist="该手机号已被注册";
    String RegisterUmExist="该用户已经存在";

}

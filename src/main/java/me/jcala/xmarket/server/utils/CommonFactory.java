package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.entity.configuration.RestIni;
import me.jcala.xmarket.server.entity.dto.Result;

/**
 * 固定静态实例产生的工厂类
 * 单例模式-饿汉式(用静态内部类保证线程安全)
 */
public class CommonFactory {

    private final Result<String> serverErr=new Result<>();
    private final Result<String> simpleSuccess=new Result<>();

    private CommonFactory(){
        this.serverErr.setCode(RestIni.serverErr);
        this.serverErr.setMsg(RestIni.serverErrMsg);
        this.simpleSuccess.setCode(RestIni.success);
    }

    private static class FactoryHolder{
       private static CommonFactory instance=new CommonFactory();
    }

    public static CommonFactory INSTANCE(){
        return FactoryHolder.instance;
    }

    public Result<String> serverError() {
        return serverErr;
    }

    public Result<String> simpleSuccess() {
        return simpleSuccess;
    }
}

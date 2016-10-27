package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.entity.dto.Result;

/**
 * 固定静态实例产生的工厂类
 * 单例模式-饿汉式(用静态内部类保证线程安全)
 */
public class CommonFactory {

   private final Result<String> error=new Result<>();

    private CommonFactory(){
        this.error.setCode(RestIni.serverErr);
        this.error.setMsg(RestIni.serverErrMsg);
    }

    private static class FactoryHolder{
       private static CommonFactory instance=new CommonFactory();
    }

    public static CommonFactory INSTANCE(){
        return FactoryHolder.instance;
    }

    public Result<String> getError() {
        return error;
    }
}

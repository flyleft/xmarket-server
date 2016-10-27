package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.entity.dto.Result;

/**
 * 自定data格式为String的Result的工厂类
 * 单例模式-饿汉式(用静态内部类保证线程安全)
 */
public class ResultStrFactory {

   private final Result<String> error=new Result<>();

    private ResultStrFactory(){
        this.error.setCode(RestIni.serverErr);
        this.error.setMsg(RestIni.serverErrMsg);
    }

    private static class FactoryHolder{
       private static ResultStrFactory instance=new ResultStrFactory();
    }

    public static ResultStrFactory INSTANCE(){
        return FactoryHolder.instance;
    }

    public Result<String> getError() {
        return error;
    }
}

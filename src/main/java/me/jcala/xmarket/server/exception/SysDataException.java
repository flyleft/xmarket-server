package me.jcala.xmarket.server.exception;

public class SysDataException extends RuntimeException{
    public SysDataException() {
    }

    public SysDataException(String message) {
        super(message);
    }

    public void error(){
        throw new SysDataException("sys集合数据不完整,请检查或者重新初始化");
    }
}

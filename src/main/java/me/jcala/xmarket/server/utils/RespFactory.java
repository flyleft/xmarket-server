package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.pojo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 固定静态实例产生的工厂类
 * 单例模式-饿汉式(用静态内部类保证线程安全)
 */
public class RespFactory {

    private final Result<String> resultSuccessStr=new Result<String>().api(Api.SUCCESS);
    private final ResponseEntity<Result> ok=new ResponseEntity<>(resultSuccessStr, HttpStatus.OK);
    private final ResponseEntity paramsError=new ResponseEntity(HttpStatus.BAD_REQUEST);
    private final ResponseEntity serverError=new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    private final ResponseEntity tokenError=new ResponseEntity(HttpStatus.UNAUTHORIZED);
    private final ResponseEntity NotFoundError=new ResponseEntity(HttpStatus.NOT_FOUND);
    private final ResponseEntity forbidden=new ResponseEntity(HttpStatus.FORBIDDEN);
    private RespFactory(){
    }

    private static class FactoryHolder{
       private static RespFactory instance=new RespFactory();
    }

    public static RespFactory INSTANCE(){
        return FactoryHolder.instance;
    }

    public ResponseEntity<Result> ok() {
        return this.ok;
    }

    public Result<String> resultSuccessStr() {
        return this.resultSuccessStr;
    }

    public ResponseEntity paramsError() {
        return paramsError;
    }

    public ResponseEntity serverError() {
        return serverError;
    }

    public ResponseEntity tokenError() {
        return tokenError;
    }

    public ResponseEntity notFoundError() {
        return NotFoundError;
    }

    public ResponseEntity forbidden() {
        return forbidden;
    }

}

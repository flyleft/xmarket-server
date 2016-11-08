package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 固定静态实例产生的工厂类
 * 单例模式-饿汉式(用静态内部类保证线程安全)
 */
public class RespFactory {

    private final Result<String> resultSuccessStr=new Result<String>().api(Api.SUCCESS);
    private final ResponseEntity<Result> getSuccess=new ResponseEntity<>(resultSuccessStr, HttpStatus.OK);
    private final ResponseEntity<Result> createSuccess=new ResponseEntity<>(new Result().api(Api.SUCCESS), HttpStatus.CREATED);
    private final ResponseEntity paramsError=new ResponseEntity(HttpStatus.BAD_REQUEST);
    private final ResponseEntity serverError=new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    private final ResponseEntity tokenError=new ResponseEntity(HttpStatus.UNAUTHORIZED);
    private final ResponseEntity NotFoundError=new ResponseEntity(HttpStatus.NOT_FOUND);
    private final ResponseEntity forbidden=new ResponseEntity(HttpStatus.FORBIDDEN);
    //private final ResponseEntity<Result> token_expired=new ResponseEntity<>(new Result().api(Api.TOKEN_ILLEGAL),HttpStatus.UNAUTHORIZED);
    private RespFactory(){
    }

    private static class FactoryHolder{
       private static RespFactory instance=new RespFactory();
    }

    public static RespFactory INSTANCE(){
        return FactoryHolder.instance;
    }

    public ResponseEntity<Result> ok() {
        return this.getSuccess;
    }

    public ResponseEntity<Result> created(){
        return this.createSuccess;
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
//    public ResponseEntity<Result> token_expired(){
//        return this.token_expired;
//    }

}

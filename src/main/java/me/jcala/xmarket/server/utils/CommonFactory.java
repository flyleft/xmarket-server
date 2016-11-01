package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 固定静态实例产生的工厂类
 * 单例模式-饿汉式(用静态内部类保证线程安全)
 */
public class CommonFactory {

    private final ResponseEntity<Result> getSuccess=new ResponseEntity<>(new Result().api(Api.SUCCESS), HttpStatus.OK);
    private final ResponseEntity<Result> createSuccess=new ResponseEntity<>(new Result().api(Api.SUCCESS), HttpStatus.CREATED);
    private final ResponseEntity<Result> serverError=new ResponseEntity<>(new Result().api(Api.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    private final ResponseEntity<Result> bad_request=new ResponseEntity<>(new Result().api(Api.ILLEGAL_PARAMS),HttpStatus.BAD_REQUEST);
    private final ResponseEntity<Result> token_expired=new ResponseEntity<>(new Result().api(Api.TOKEN_EXPIRED),HttpStatus.UNAUTHORIZED);
    private final ResponseEntity<Result> forbidden=new ResponseEntity<>(new Result().api(Api.FORBIDDEN),HttpStatus.FORBIDDEN);
    private CommonFactory(){
    }

    private static class FactoryHolder{
       private static CommonFactory instance=new CommonFactory();
    }

    public static CommonFactory INSTANCE(){
        return FactoryHolder.instance;
    }

    public ResponseEntity<Result> ok() {
        return this.getSuccess;
    }

    public ResponseEntity<Result> created(){
        return this.createSuccess;
    }
    public ResponseEntity<Result> serverError(){
        return this.serverError;
    }
    public ResponseEntity<Result> bad_request(){
        return this.bad_request;
    }
    public ResponseEntity<Result> token_expired(){
        return this.token_expired;
    }
    public ResponseEntity<Result> forbidden(){
        return this.forbidden;
    }
}

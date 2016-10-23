package me.jcala.xmarket.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回给client的json对应的javabean
 */
@Setter
@Getter
@AllArgsConstructor
public class Result<T> {
    private int code;//返回码
    private String msg;//返回信息
    private T data;//返回数据

    public Result() {
        this.msg="";
    }
}

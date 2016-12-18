package me.jcala.xmarket.server.entity.pojo;

import lombok.*;
import me.jcala.xmarket.server.entity.configuration.Api;

/**
 * 返回给client的json对应的javabean
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> {
    @Getter
    private int code;//返回码
    @Getter
    private String msg;//返回信息
    @Setter
    @Getter
    private T data;//返回数据

    public Result<T> api(Api api) {
        this.code=api.code();
        this.msg=api.msg();
        return this;
    }
}

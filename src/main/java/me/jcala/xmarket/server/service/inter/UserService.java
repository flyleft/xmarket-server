package me.jcala.xmarket.server.service.inter;


import me.jcala.xmarket.server.entity.dto.Result;

public interface UserService {

    Result<String> login(String username,String password);//用户登录

    Result<String> register(String username,String password,String phone);//用户注册
}

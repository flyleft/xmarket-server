package me.jcala.xmarket.server.service.inter;


import me.jcala.xmarket.server.dto.Result;

public interface UserService {

    //用户登录
    Result<String> login(String password);
}

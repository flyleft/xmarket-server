package me.jcala.xmarket.mongo.service.inter;

import me.jcala.xmarket.mongo.dto.Result;

public interface UserService {

    //用户登录
    Result<String> login(String password);
}

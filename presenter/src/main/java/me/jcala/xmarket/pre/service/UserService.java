package me.jcala.xmarket.pre.service;

import me.jcala.xmarket.pre.entity.Result;

public interface UserService {

    //用户登录
    Result<String> login(String password);
}

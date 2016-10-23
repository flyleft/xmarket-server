package me.jcala.xmarket.dao.mongo.service;

import me.jcala.xmarket.dao.mongo.entity.Result;

public interface UserService {

    //用户登录
    Result<String> login(String password);
}

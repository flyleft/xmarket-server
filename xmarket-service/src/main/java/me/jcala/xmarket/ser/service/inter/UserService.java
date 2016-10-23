package me.jcala.xmarket.ser.service.inter;

import me.jcala.xmarket.ser.dto.Result;

public interface UserService {

    //用户登录
    Result<String> login(String password);
}

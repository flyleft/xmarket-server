package me.jcala.xmarket.server.service.inter;


import me.jcala.xmarket.server.entity.dto.Result;

public interface UserService {

    Result<String> login(String username,String password) throws RuntimeException;//用户登录

    Result<String> register(String username,String password,String phone) throws RuntimeException;//用户注册

    Result<String> updateSchool(String username,String school) throws RuntimeException;//
}

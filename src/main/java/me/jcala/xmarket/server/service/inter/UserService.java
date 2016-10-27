package me.jcala.xmarket.server.service.inter;


import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.dto.Result;

import java.util.List;

public interface UserService {

    Result<String> login(String username,String password) throws RuntimeException;//用户登录

    Result<String> register(String username,String password,String phone) throws RuntimeException;//用户注册

    Result<String> updateUserSchool(String username,String school) throws RuntimeException;//设置用户所在的学校

    Result<List<String>> gainSchoolList() throws RuntimeException;//获取学校的列表

    Result<String> updateInfo(User user)throws RuntimeException;//更新用户信息
    
}

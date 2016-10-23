package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.dto.Result;
import me.jcala.xmarket.server.dao.User;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Result<String> login(String username,String password) {

        return null;
    }

    public Result<String> register(User user){

       return null;
    }

}

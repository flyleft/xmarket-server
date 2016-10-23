package me.jcala.xmarket.dao.mongo.service.impl;

import me.jcala.xmarket.dao.mongo.entity.Result;
import me.jcala.xmarket.dao.mongo.entity.User;
import me.jcala.xmarket.dao.mongo.repository.UserRepository;
import me.jcala.xmarket.dao.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public Result<String> login(String password) {
        List<User> userList=userRepository.findAll();
        for (User user:userList){
            System.out.println(user);
        }
        return null;
    }


}

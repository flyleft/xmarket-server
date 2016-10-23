package me.jcala.xmarket.mongo.service;

import me.jcala.xmarket.mongo.dao.User;
import me.jcala.xmarket.mongo.repository.UserRepository;
import me.jcala.xmarket.mongo.dto.Result;
import me.jcala.xmarket.mongo.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public Result<String> login(String password) {
        List<User> userList=repository.findAll();
        for (User user:userList){
            System.out.println(user);
        }
       System.out.println("service 注入");
        return null;
    }


}

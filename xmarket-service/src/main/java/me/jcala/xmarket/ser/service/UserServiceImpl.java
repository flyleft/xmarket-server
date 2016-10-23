package me.jcala.xmarket.ser.service;

import me.jcala.xmarket.mongo.repository.TestRepository;
import me.jcala.xmarket.ser.dto.Result;
import me.jcala.xmarket.ser.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    TestRepository repository;

    @Override
    public Result<String> login(String password) {
       /* List<User> userList=userRepository.findAll();
        for (User user:userList){
            System.out.println(user);
        }
       System.out.println("service 注入");*/
       repository.say();
        return null;
    }


}

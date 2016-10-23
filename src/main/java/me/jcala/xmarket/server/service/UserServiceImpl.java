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
    public Result<String> login(String password) {
        List<User> userList=userRepository.findAll();
        for (User user:userList){
            System.out.println(user);
        }
        return null;
    }


}

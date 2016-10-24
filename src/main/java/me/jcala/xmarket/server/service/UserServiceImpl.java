package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.conf.RestIni;
import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public Result<String> login(String username,String password) throws Exception{
        long num=userRepository.countByUsernameAndPassword(username,password);
        Result<String> result=new Result<>();
        if (num>0){
            result.setCode(RestIni.success); return result;
        }else if (userRepository.countUserByUsername(username)>0){
            result.setMsg(RestIni.loginPassErr);
            return result;
        }else {
            result.setMsg(RestIni.loginUmErr);
            return result;
        }
    }

    @Override
    public Result<String> register(String username, String password, String phone) throws Exception{
        Result<String> result=new Result<>();
        if (userRepository.countUserByUsername(username)>0){
           result.setMsg(RestIni.RegisterUmExist);
            return result;
        }else if (userRepository.countByPhone(phone)>0){
            result.setMsg(RestIni.RegisterPhoneExist);
            return result;
        }else {
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setPhone(phone);
            userRepository.save(user);
            result.setCode(RestIni.success);
            return result;
        }
    }
}

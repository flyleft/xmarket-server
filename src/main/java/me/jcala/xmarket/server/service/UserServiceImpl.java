package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import me.jcala.xmarket.server.entity.document.UserBuilder;
import me.jcala.xmarket.server.entity.dto.ResultBuilder;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.utils.CommonFactory;
import me.jcala.xmarket.server.utils.RestIni;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.CustomRepositoryImpl;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SystemRepository systemRepository;

    private CustomRepositoryImpl customRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SystemRepository systemRepository,
                           CustomRepositoryImpl customRepository) {
        this.userRepository = userRepository;
        this.systemRepository = systemRepository;
        this.customRepository = customRepository;
    }

    @Override
    public Result<String> login(String username,String password)
            throws RuntimeException{
        long num=userRepository.countByUsernameAndPassword(username,password);
        Result<String> result=new Result<>();
        if (num>0){
            result.setCode(RestIni.success); return result;
        }else if (userRepository.countByUsername(username)>0){
            result.setMsg(RestIni.loginPassErr);
            return result;
        }else {
            result.setMsg(RestIni.loginUmErr);
            return result;
        }
    }

    @Override
    public Result<String> register(String username,String password,String phone)
            throws RuntimeException{
        Result<String> result=new Result<>();
        if (userRepository.countByUsername(username)>0){
           result.setMsg(RestIni.RegisterUmExist);
            return result;
        }else if (userRepository.countByPhone(phone)>0){
            result.setMsg(RestIni.RegisterPhoneExist);
            return result;
        }else {
            userRepository.insert(
                    new UserBuilder()
                            .username(username)
                            .password(password)
                            .phone(phone)
                            .build()
            );
            result.setCode(RestIni.success);
            return result;
        }
    }

    @Override
    public Result<String> updateUserSchool(String username,String school)
            throws RuntimeException {
       customRepository.updateUserSchool(username,school);
        return CommonFactory.INSTANCE().simpleSuccess();
    }

    @Override
    public Result<List<String>> gainSchoolList() throws RuntimeException {
        String name= SysColName.COL_SCHOOL.name().toLowerCase();
        SystemBean bean=systemRepository.findByName(name);
        if (bean==null||bean.getSchools()==null){
            throw new SysDataException("sys集合数据不完整,请检查或者重新初始化");
        }
        return new ResultBuilder<List<String>>().Code(RestIni.success)
                                                .data(bean.getSchools())
                                                .build();
    }

    @Override
    public Result<String> modifyPassword(String username,String oldPass,String newPass)
            throws RuntimeException {
        long num=userRepository.countByUsernameAndPassword(username,oldPass);
        if (num<1){
            return new ResultBuilder<String>().msg(RestIni.modifyPassErr).build();
        }
        customRepository.updateUserPassword(username,newPass);
        return CommonFactory.INSTANCE().simpleSuccess();
    }
}

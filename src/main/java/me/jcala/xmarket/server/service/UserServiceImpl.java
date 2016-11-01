package me.jcala.xmarket.server.service;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.configuration.RestIni;
import me.jcala.xmarket.server.entity.document.UserBuilder;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.entity.dto.ResultBuilder;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.exception.TestException;
import me.jcala.xmarket.server.repository.CustomRepositoryImpl;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.CommonFactory;
import me.jcala.xmarket.server.utils.StaticTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SystemRepository systemRepository;

    private CustomRepositoryImpl customRepository;

    private ApplicationInfo info;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SystemRepository systemRepository,
                           CustomRepositoryImpl customRepository, ApplicationInfo info) {
        this.userRepository = userRepository;
        this.systemRepository = systemRepository;
        this.customRepository = customRepository;
        this.info = info;
    }

    @Override
    public Result<String> login(String username, String password){
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
    public Result<String> register(String username,String password,String phone){
        Result<String> result=new Result<>();
        if (userRepository.countByUsername(username)>0){
           result.setMsg(RestIni.RegisterUmExist);
           throw new TestException("测试springMVC的异常自动上抛");
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
    public Result<String> updateSchool(String username, String school){
       customRepository.updateUserSchool(username,school);
        return CommonFactory.INSTANCE().simpleSuccess();
    }

    @Override
    public Result<List<String>> gainSchoolList(){
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
    public Result<String> updatePassword(String username, String oldPass, String newPass)
            throws RuntimeException {
        long num=userRepository.countByUsernameAndPassword(username,oldPass);
        if (num<1){
            return new ResultBuilder<String>().msg(RestIni.modifyPassErr).build();
        }
        customRepository.updateUserPassword(username,newPass);
        return CommonFactory.INSTANCE().simpleSuccess();
    }

    @Override
    public Result<String> updateAvatar(String username, HttpServletRequest request)
            throws RuntimeException {
        String url="";
        try {
            url=StaticTool.updateAvatar("/api/user/avatar/",info.getPicHome(),request);
        } catch (Exception e) {
            log.warn("上传"+username+"用户头像发生错误"+e.getLocalizedMessage());
        }
        return new ResultBuilder<String>().Code(RestIni.success).data(url).build();
    }

}

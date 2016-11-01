package me.jcala.xmarket.server.service;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.document.UserBuilder;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.repository.CustomRepositoryImpl;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.CommonFactory;
import me.jcala.xmarket.server.utils.FieldValidator;
import me.jcala.xmarket.server.utils.StaticTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;
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
            result.api(Api.SUCCESS);
            return result;
        }else if (userRepository.countByUsername(username)>0){
            result.api(Api.USER_PASS_ERR);
            return result;
        }else {
            result.api(Api.USER_NAME_EXIST);
            return result;
        }
    }

    /**
     * POST /users/register                  用户注册
     注册成功:       自定义状态码100  HttpStatus201 content包含user_id
     用户名已存在:    自定义状态码205  HttpStatus202
     手机号已经被注册: 自定义状态码206  HttpStatus202
     操作异常:        自定义状态码101  HttpStatus500
     参数错误:        自定义状态码103  HttpStatus400
     */
    @Override
    public ResponseEntity<?> register(String username, String password, String phone){
        Result<String> result=new Result<>();
        if (FieldValidator.hasEmpty(username,password,phone)){
            result.api(Api.ILLEGAL_PARAMS);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }else if (userRepository.countByUsername(username)>0){
           result.api(Api.USER_NAME_EXIST);
           return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        }else if (userRepository.countByPhone(phone)>0){
            result.api(Api.USER_PHONE_EXIST);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        }else {
                userRepository.insert(
                        new UserBuilder()
                                .username(username)
                                .password(password)
                                .phone(phone)
                                .build()
                );
            result.api(Api.SUCCESS);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateSchool(String id, String school){
       customRepository.updateUserSchool(id,school);
       return null;
    }

    /**
     GET /school_list                          获取学校列表
     获取成功:       自定义状态码100  HttpStatus200 content包含school列表
     获取失败:       自定义状态码101  HttpStatus500
     */
    @Override
    public ResponseEntity<?>gainSchoolList(){
        String name= SysColName.COL_SCHOOL.name().toLowerCase();
        SystemBean bean=systemRepository.findByName(name);
        if (bean==null||bean.getSchools()==null){
            throw new SysDataException("sys集合数据不完整,请检查或者重新初始化");
        }
        Result<List<String>> result=new Result<List<String>>().api(Api.SUCCESS);
        result.setData(bean.getSchools());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePassword(String id, String oldPass, String newPass){
        long num=userRepository.countByIdAndPassword(id,oldPass);
        if (num<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_OLD_PASS_ERR),HttpStatus.UNAUTHORIZED);
        }
        customRepository.updateUserPassword(id,newPass);
        return new ResponseEntity<>(new Result<String>().api(Api.SUCCESS),HttpStatus.CREATED);
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

package me.jcala.xmarket.server.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.document.UserBuilder;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.CustomRepositoryImpl;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserInfoService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import me.jcala.xmarket.server.utils.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserRepository userRepository;

    private CustomRepositoryImpl customRepository;

    private ApplicationInfo info;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository,
                               CustomRepositoryImpl customRepository, ApplicationInfo info) {
        this.userRepository = userRepository;
        this.customRepository = customRepository;
        this.info = info;
    }

    @Override
    public ResponseEntity<?> loginAndGetToken(String username, String password) {
        if (CustomValidator.hasEmpty(username,password)){
            return RespFactory.INSTANCE().paramsError();
        }
        if (userRepository.countByUsername(username)<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.OK);
        }
        Optional<User> user=userRepository.findByUsernameAndPassword(username,password);
        if (!user.isPresent()){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_PASS_ERR),HttpStatus.OK);
        }else {
            User userData=user.get();
            String token=createJWT("xmarket","jcala",userData.getId(),info.getJwtLife());
            Result<User> result=new Result<>();
            result.api(Api.SUCCESS);
            userData.setToken(token);
            result.setData(userData);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }
    /**
     *
     * @param id         密钥 ID
     * @param issuer     jwt签发者
     * @param subject    所要传输的数据
     * @param ttlMillis  token生命周期(单位:毫秒)
     * @return 加密后JWT token字符串
     */
    private String createJWT(String id, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(info.getJwtKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    @Override
    public ResponseEntity<?> register(String username, String password){
        if (CustomValidator.hasEmpty(username,password)){
            return RespFactory.INSTANCE().paramsError();
        }else if (userRepository.countByUsername(username)>0){
           return new ResponseEntity<>(new Result<String>().api(Api.USER_NAME_EXIST),HttpStatus.OK);
        }else {
                User user=userRepository.insert(
                        new UserBuilder()
                                .username(username)
                                .password(password)
                                .avatarUrl("/img/avatar.jpg")//设置用户默认头像，即/resources/static/img/avatar.jpg
                                .build()
                );

                if (user==null || user.getId()==null){
                  throw new RuntimeException("UserInfoServiceImpl:用户注册向数据库插入数据时发生异常");
                }
            Result<String> result=new Result<>();
            result.api(Api.SUCCESS);
            result.setData(user.getId());
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> updatePhoneSchool(String id,String phone, String school){

        if (CustomValidator.hasEmpty(id,school)){
            return RespFactory.INSTANCE().paramsError();
        }
        User user=userRepository.findOne(id);
        if (user==null){
            return RespFactory.INSTANCE().notFoundError();
        }
        customRepository.updateUserPhoneSchool(id,phone,school);
        Result<User> userResult=new Result<User>().api(Api.SUCCESS);
        String token=createJWT("xmarket","jcala",user.getId(),info.getJwtLife());
        user.setToken(token);
        user.setSchool(school);
        user.setPhone(phone);
        userResult.setData(user);
        return new ResponseEntity<>(userResult,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePassword(String id, String oldPass, String newPass){
        if (CustomValidator.hasEmpty(id,oldPass,newPass)){
            return RespFactory.INSTANCE().paramsError();
        }
        long num=userRepository.countByIdAndPassword(id,oldPass);
        if (num<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_OLD_PASS_ERR),HttpStatus.OK);
        }else if (userRepository.countById(id)<1){
            return RespFactory.INSTANCE().notFoundError();
        }
        customRepository.updateUserPassword(id,newPass);
        return RespFactory.INSTANCE().ok();
    }

    @Override
    public ResponseEntity<?> updateAvatar(String id, HttpServletRequest request) throws Exception{
        if (CustomValidator.hasEmpty(id)){
            return RespFactory.INSTANCE().paramsError();
        }
        if (userRepository.countById(id)<1){
            return RespFactory.INSTANCE().notFoundError();
        }
        String url= FileTool.uploadFile(info.getPicHome(),request);
        customRepository.updateUserAvatar(id,url);
        return RespFactory.INSTANCE().ok();
    }

}

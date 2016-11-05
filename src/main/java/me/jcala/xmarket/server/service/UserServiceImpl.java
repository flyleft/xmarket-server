package me.jcala.xmarket.server.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.document.UserBuilder;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.repository.CustomRepositoryImpl;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import me.jcala.xmarket.server.utils.StaticTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    /**
     POST /users/login                     用户登录
     登录成功:   自定义状态码100  HttpStatus200  content包含access_token
     用户名错误: 自定义状态码202  HttpStatus404
     密码错误:   自定义状态码203  HttpStatus401
     操作异常:   自定义状态码101  HttpStatus500
     参数错误:   自定义状态码103  HttpStatus400
     */
    @Override
    public ResponseEntity<?> loginAndGetToken(String username, String password) {
        if (CustomValidator.hasEmpty(username,password)){
            return RespFactory.INSTANCE().illegal_params();
        }
        if (userRepository.countByUsername(username)<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.NOT_FOUND);
        }
        Optional<User> user=userRepository.findByUsernameAndPassword(username,password);
        if (!user.isPresent()){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_PASS_ERR),HttpStatus.UNAUTHORIZED);
        }else {
            String token=createJWT("xmarket","jcala",user.get().getId(),info.getJwtLife());
            Result<String> result=new Result<>();
            result.api(Api.SUCCESS);
            result.setData(token);
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
        String appKey = new BASE64Encoder().encode(info.getJwtKey().getBytes());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(appKey);

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
        if (CustomValidator.hasEmpty(username,password,phone)){
            return RespFactory.INSTANCE().illegal_params();
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
            return RespFactory.INSTANCE().created();
        }
    }

    /**
     *PUT /users/user_id/update_school         更新学校信息
     更新成功:       自定义状态码100  HttpStatus201 content包含user info
     用户不存在:     自定义状态码201  HttpStatus404
     无操作权限:     自定义状态码102  HttpStatus403
     操作异常:       自定义状态码101  HttpStatus500
     参数错误:       自定义状态码103  HttpStatus400
     */
    @Override
    public ResponseEntity<?> updateSchool(String id, String school){
        if (CustomValidator.hasEmpty(id,school)){
            return RespFactory.INSTANCE().illegal_params();
        }else if (userRepository.countById(id)<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST),HttpStatus.NOT_FOUND);
        }
        customRepository.updateUserSchool(id,school);
        return RespFactory.INSTANCE().created();
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

    /**
     PUT /users/user_id/update_pass               更新用户密码
     更新成功:       自定义状态码100  HttpStatus201 content不包含内容
     原密码错误:     自定义状态码204  HttpStatus401
     用户不存在:     自定义状态码201  HttpStatus404
     无操作权限:     自定义状态码102  HttpStatus403
     操作异常:       自定义状态码101  HttpStatus500
     参数错误:       自定义状态码103  HttpStatus400
     */
    @Override
    public ResponseEntity<?> updatePassword(String id, String oldPass, String newPass){
        if (CustomValidator.hasEmpty(id,oldPass,newPass)){
            return RespFactory.INSTANCE().illegal_params();
        }
        long num=userRepository.countByIdAndPassword(id,oldPass);
        if (num<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_OLD_PASS_ERR),HttpStatus.UNAUTHORIZED);
        }else if (userRepository.countById(id)<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST),HttpStatus.NOT_FOUND);
        }
        customRepository.updateUserPassword(id,newPass);
        return RespFactory.INSTANCE().created();
    }

    /**
     更新成功:       自定义状态码100  HttpStatus201 content包含头像url
     用户不存在:     自定义状态码201  HttpStatus404
     无操作权限:     自定义状态码102  HttpStatus403
     操作异常:       自定义状态码101  HttpStatus500
     参数错误:       自定义状态码103  HttpStatus400
     */
    @Override
    public ResponseEntity<?> updateAvatar(String id, HttpServletRequest request) throws Exception{
        if (CustomValidator.hasEmpty(id)){
            return RespFactory.INSTANCE().illegal_params();
        }else if (userRepository.countById(id)<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST),HttpStatus.NOT_FOUND);
        }
        String url=StaticTool.updateAvatar("/api/user/avatar/",info.getPicHome(),request);
        customRepository.updateUserAvatar(id,url);
        return RespFactory.INSTANCE().created();
    }
    /**
     * 将jwt解析为javaBean
     */
    /*private Token parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(info.getJwtKey()))
                .parseClaimsJws(jwt).getBody();
        return Token.builder()
                .id(claims.getId())
                .issuer(claims.getIssuer())
                .expiration(claims.getExpiration())
                .subject(claims.getSubject())
                .build();
    }*/

}

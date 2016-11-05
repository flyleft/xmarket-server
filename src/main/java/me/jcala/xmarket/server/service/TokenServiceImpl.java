package me.jcala.xmarket.server.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.entity.pojo.Token;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.TokenService;
import me.jcala.xmarket.server.utils.FieldValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService{

    private UserRepository userRepository;

    private ApplicationInfo info;

    @Autowired
    public TokenServiceImpl(UserRepository userRepository, ApplicationInfo info) {
        this.userRepository = userRepository;
        this.info = info;
    }

    @Override
    public ResponseEntity<?> createToken(String username,String password,String ipMac) {
        if (FieldValidator.hasEmpty(username,password,ipMac)){
            return RespFactory.INSTANCE().illegal_params();
        }else if (userRepository.countByUsername(username)<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.NOT_FOUND);
        }else if (userRepository.countByIdAndPassword(username,password)>0){
            String token=createJWT("xmarket","jcala",username+":"+ipMac,info.getJwtLife());
            Result<String> result=new Result<>();
            result.api(Api.SUCCESS);
            result.setData(token);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result<String>().api(Api.USER_PASS_ERR),HttpStatus.UNAUTHORIZED);
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
     * 将jwt解析为javaBean
     */
    private Token parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(info.getJwtKey()))
                .parseClaimsJws(jwt).getBody();
       return Token.builder()
                .id(claims.getId())
                .issuer(claims.getIssuer())
                .expiration(claims.getExpiration())
                .subject(claims.getSubject())
                .build();
    }

    @Override
    public boolean JwtVerify(String jwt){
        boolean trust=true;
        try {
            Jwts.parser().setSigningKey(info.getJwtKey()).parseClaimsJws(jwt);
        } catch (SignatureException e) {
            trust=false;
        }
        return trust;
    }
}

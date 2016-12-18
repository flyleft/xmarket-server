package me.jcala.xmarket.server.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.TokenVerifyResult;
import me.jcala.xmarket.server.entity.document.Message;

import javax.xml.bind.DatatypeConverter;

@Slf4j
public class CustomValidator {

    public static boolean hasEmpty(String...strings){
        for (String str:strings){
            if (str==null||str.isEmpty()){
                return true;
            }
        }
        return false;
    }
    public static boolean hasNull(Object...objects){
        for (Object object:objects){
            if(object==null){
                return true;
            }
        }
        return false;
    }
    public static TokenVerifyResult JwtVerify(final String key, final String jwt){
        try {
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(jwt);
        } catch (ExpiredJwtException e) {
            return TokenVerifyResult.expired;
        } catch (SignatureException e) {
            return TokenVerifyResult.illegalSignature;
        }
        return TokenVerifyResult.success;
    }

    public static boolean checkConfirmMessage(Message message){
        // TODO: 16-12-18  没有实现验证确认交易的Message方法
        if (message==null){
            return false;
        }
        return true;
    }
}

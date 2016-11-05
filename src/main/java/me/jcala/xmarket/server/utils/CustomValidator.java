package me.jcala.xmarket.server.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;

@Slf4j
public class CustomValidator {

    private static ApplicationInfo info;

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
    public static boolean JwtVerify(final String key,final String jwt){
        boolean trust=true;
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        } catch (SignatureException e) {
            trust=false;
        }
        return trust;
    }
}

package me.jcala.xmarket.server.entity;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.jcala.xmarket.server.entity.configuration.TokenVerifyResult;
import me.jcala.xmarket.server.utils.CustomValidator;
import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtTest {
    private final String key="dsjkdls;ld;sldlsdls;d";
    private String token;

    private String createJWT(String id, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
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

    @Test
    public void verifyToken(){
        token=createJWT("xmarket","jcala","jsadjsdkslodlsd",30000);
        System.out.println(token);
        TokenVerifyResult result=CustomValidator.JwtVerify(key,token);
        System.out.println(result);
    }

}

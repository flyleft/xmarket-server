package me.jcala.xmarket.server.entity;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtTest {
    @Test
    public void testJwt(){
        Key key = MacProvider.generateKey();

        System.out.println(key.getAlgorithm());
        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.print(compactJws);
    }
    @Test
    public void testJwtInJava(){
        System.out.println(createJWT("xmarket","34","jcala",System.currentTimeMillis()+10));
    }
    private String createJWT(String id, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String appKey = new BASE64Encoder().encode("jcala".getBytes());
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
}

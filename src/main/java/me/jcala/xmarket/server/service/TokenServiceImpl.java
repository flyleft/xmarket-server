package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.service.inter.TokenService;
import org.springframework.http.ResponseEntity;

public class TokenServiceImpl implements TokenService{

    @Override
    public ResponseEntity<?> createToken(String username,String password) {


        return null;
    }
}

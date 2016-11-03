package me.jcala.xmarket.server.service.inter;

import org.springframework.http.ResponseEntity;

public interface TokenService {
    ResponseEntity<?> createToken(String username,String password);
}

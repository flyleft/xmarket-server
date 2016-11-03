package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import me.jcala.xmarket.server.service.inter.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("用于生成JWT Token")
@RestController
public class JwtTokenController {

    private TokenService tokenService;

    @Autowired
    public JwtTokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/authenticate",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(String username,String password){

       return tokenService.createToken(username,password);
    }
}

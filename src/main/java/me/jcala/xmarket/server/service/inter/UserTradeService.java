package me.jcala.xmarket.server.service.inter;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserTradeService {

    ResponseEntity<?> createTrade(String userId,String trade,HttpServletRequest request);


}

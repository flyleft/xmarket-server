package me.jcala.xmarket.server.service.inter;

import me.jcala.xmarket.server.entity.configuration.TradeType;
import me.jcala.xmarket.server.entity.document.Trade;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserTradeService {

    ResponseEntity<?> createTrade(String userId,String trade,HttpServletRequest request);

    ResponseEntity<?> getTrades(TradeType type,String userId);

}

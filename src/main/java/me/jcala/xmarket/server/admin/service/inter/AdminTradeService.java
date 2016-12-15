package me.jcala.xmarket.server.admin.service.inter;

import me.jcala.xmarket.server.admin.entity.TradeTag;
import org.springframework.http.ResponseEntity;

public interface AdminTradeService {

    ResponseEntity<?> addTradeTag(TradeTag tag);

}

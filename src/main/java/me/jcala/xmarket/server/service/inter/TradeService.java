package me.jcala.xmarket.server.service.inter;

import org.springframework.http.ResponseEntity;

public interface TradeService {
    ResponseEntity<?> getTradeListBySort(String sortId);//根据分类id获取该分类下的商品列表
    ResponseEntity<?> getTradeDetailById(String tradeId);//通过Id获取该商品所有信息
    ResponseEntity<?> getTradeListBySchoolName(String schoolName);
}

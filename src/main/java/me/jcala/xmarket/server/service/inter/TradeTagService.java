package me.jcala.xmarket.server.service.inter;

import org.springframework.http.ResponseEntity;

public interface TradeTagService {
    ResponseEntity<?> getTradeSortList();//获取商品分类列表
    ResponseEntity<?> getTradeListBySort(String sortId);//根据分类id获取该分类下的商品列表
}

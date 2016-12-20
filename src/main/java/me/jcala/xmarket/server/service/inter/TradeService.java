package me.jcala.xmarket.server.service.inter;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface TradeService {

    ResponseEntity<?> getTradeListByTagName(String tagName, Pageable page);//根据分类id获取该分类下的商品列表

    ResponseEntity<?> getTradeDetailById(String tradeId);//通过Id获取该商品所有信息

    ResponseEntity<?> getTradeListBySchoolName(String schoolName, Pageable page);//根据学校名称获取该学校下商品

    ResponseEntity<?> getTradeListByTeamName(String team,Pageable page);//获取该志愿队下受赠商品列表

    ResponseEntity<?> createTrade(String trade,HttpServletRequest request);//发布商品

}

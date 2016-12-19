package me.jcala.xmarket.server.service.inter;

import me.jcala.xmarket.server.entity.document.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface HybridService {
    //----------------------------------分类相关--------------------------------
    ResponseEntity<?> getTradeTagList();//获取商品分类列表(List<TradeTag>)
    ResponseEntity<?> getTradeTagNameList();//获取商品分类名字列表(List<String>)

    //----------------------------------志愿队相关-------------------------------
    ResponseEntity<?> getTeamListBySchoolName(String schoolName,int type,Pageable page);//获取志愿队列表
    ResponseEntity<?> createTeam(String team, HttpServletRequest request);//添加志愿队

    //-----------------------------------学校相关--------------------------------
    ResponseEntity<?> getSchoolList();//获取学校列表

    //-----------------------------------文件相关--------------------------------
    ResponseEntity<byte[]> gainPic(String dir, String picName);

    //-----------------------------------交易相关---------------------------------
    ResponseEntity<?> createDeal(String fromId,String fromName,String fromAvatar,String tradeId);//发起交易
    ResponseEntity<?> confirmDeal(Message message);//确认交易
}

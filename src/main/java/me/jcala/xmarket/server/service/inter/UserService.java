package me.jcala.xmarket.server.service.inter;


import me.jcala.xmarket.server.entity.configuration.TradeType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
public interface UserService {


    ResponseEntity<?> loginAndGetToken(String username,String password);

    ResponseEntity<?> register(String username,String password);//用户注册

    ResponseEntity<?> updatePhoneSchool(String id, String phone,String school);//设置用户所在的学校

    ResponseEntity<?> updatePassword(String username, String oldPass, String newPass);//更新用户信息

    ResponseEntity<?> updateAvatar(String username, HttpServletRequest request) throws Exception;//更新用户头像

    ResponseEntity<?> getTrades(TradeType type, String userId);//获取用户在售，已卖，已买，捐赠，待确认的商品列表

    ResponseEntity<?> getMessages(String userId, int msgNum, Pageable page);

}

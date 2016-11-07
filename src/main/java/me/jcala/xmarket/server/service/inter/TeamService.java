package me.jcala.xmarket.server.service.inter;

import org.springframework.http.ResponseEntity;

/**
 * 志愿队相关的service
 */
public interface TeamService {

    ResponseEntity<?> getTeamList();//获取志愿队列表

    ResponseEntity<?> createTeam();//添加志愿队

}

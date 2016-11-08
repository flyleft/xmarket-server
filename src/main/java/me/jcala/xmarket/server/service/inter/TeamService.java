package me.jcala.xmarket.server.service.inter;

import me.jcala.xmarket.server.entity.document.Team;
import org.springframework.http.ResponseEntity;

/**
 * 志愿队相关的service
 */
public interface TeamService {

    ResponseEntity<?> getTeamListBySchoolName(String schoolName);//获取志愿队列表

    ResponseEntity<?> createTeam(Team team);//添加志愿队

}

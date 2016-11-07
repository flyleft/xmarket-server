package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.document.Team;
import me.jcala.xmarket.server.service.inter.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService{
    @Override
    public ResponseEntity<?> getTeamList() {
        return null;
    }

    @Override
    public ResponseEntity<?> createTeam(Team team) {

        return null;
    }
}

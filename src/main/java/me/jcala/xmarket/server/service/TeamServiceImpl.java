package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.document.Team;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.CustomRepository;
import me.jcala.xmarket.server.repository.TeamRepository;
import me.jcala.xmarket.server.service.inter.TeamService;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{

    private TeamRepository teamRepository;

    private CustomRepository customRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, CustomRepository customRepository) {
        this.teamRepository = teamRepository;
        this.customRepository = customRepository;
    }

    @Override
    public ResponseEntity<?> getTeamList() {
        List<Team> teamList=teamRepository.findAll();
        Result<List<Team>> result=new Result<List<Team>>().api(Api.SUCCESS);
        result.setData(teamList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createTeam(Team team) {
        if (team==null){
            return RespFactory.INSTANCE().illegal_params();
        }
        Team teamData=teamRepository.save(team);
        if (teamData==null){
            throw new RuntimeException("添加Team数据时发生了异常!");
        }
        customRepository.updateUserTeams(team.getSponsor().getId(),teamData.getId());

        return RespFactory.INSTANCE().created();
    }
}

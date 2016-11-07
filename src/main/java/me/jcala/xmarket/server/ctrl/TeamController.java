package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.jcala.xmarket.server.entity.document.Team;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("跟志愿队有关的api")
@RestController
@RequestMapping("/api/v1/")
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ApiOperation(value = "创建一个新的志愿队",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/teams",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createTeam(Team team){
        return teamService.createTeam(team);
    }

}

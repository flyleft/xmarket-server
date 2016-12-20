package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamRepository extends MongoRepository<Team,String>{

    List<Team> findAllBySchoolAndStatus(String school, boolean status, Pageable pageable) ;

    Team findByName(String name);
}
